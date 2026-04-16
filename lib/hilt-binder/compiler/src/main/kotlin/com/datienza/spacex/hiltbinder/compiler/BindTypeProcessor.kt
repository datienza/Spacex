package com.datienza.spacex.hiltbinder.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate

private const val CONTRIBUTES_BINDING = "com.datienza.spacex.hiltbinder.ContributesBinding"

/** Known Dagger scope annotation FQNs — used to forward scope to the generated `@Binds`. */
private val SCOPE_ANNOTATIONS = setOf(
    "javax.inject.Singleton",
    "dagger.hilt.android.scopes.ActivityScoped",
    "dagger.hilt.android.scopes.ActivityRetainedScoped",
    "dagger.hilt.android.scopes.FragmentScoped",
    "dagger.hilt.android.scopes.ViewModelScoped",
    "dagger.hilt.android.scopes.ServiceScoped",
    "dagger.Reusable",
)

class BindTypeProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(CONTRIBUTES_BINDING)
        val deferred = symbols.filterNot { it.validate() }.toList()

        symbols
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.validate() }
            .forEach { processClass(it) }

        return deferred
    }

    private fun processClass(declaration: KSClassDeclaration) {
        val annotation = declaration.annotations.first { it.isContributesBinding() }

        val scope = annotation.argument("scope") ?: run {
            logger.error("@ContributesBinding: missing 'scope' on ${declaration.simpleName.asString()}", declaration)
            return
        }
        val boundType = resolveBoundType(declaration, annotation) ?: return
        val scopeAnnotation = findScopeAnnotation(declaration)

        val implSimple = declaration.simpleName.asString()
        val pkg = declaration.packageName.asString()
        val scopeQualified = scope.declaration.qualifiedName!!.asString()
        val scopeSimple = scope.declaration.simpleName.asString()
        val boundQualified = boundType.declaration.qualifiedName!!.asString()
        val boundPkg = boundType.declaration.packageName.asString()
        val boundTypeStr = renderType(boundType)
        val moduleName = "${implSimple}_HiltBinderModule"

        val imports = buildSet {
            add("dagger.Binds")
            add("dagger.Module")
            add("dagger.hilt.InstallIn")
            add(scopeQualified)
            scopeAnnotation?.let { add(it) }
            if (boundPkg != pkg) add(boundQualified)
        }

        val code = buildString {
            appendLine("package $pkg")
            appendLine()
            imports.sorted().forEach { appendLine("import $it") }
            appendLine()
            appendLine("@Module")
            appendLine("@InstallIn($scopeSimple::class)")
            appendLine("internal interface $moduleName {")
            append("    @Binds")
            scopeAnnotation?.let {
                appendLine()
                append("    @${it.substringAfterLast('.')}")
            }
            appendLine()
            appendLine("    fun bind(impl: $implSimple): $boundTypeStr")
            appendLine("}")
        }

        codeGenerator.createNewFile(
            dependencies = Dependencies(aggregating = false, declaration.containingFile!!),
            packageName = pkg,
            fileName = moduleName,
        ).bufferedWriter().use { it.write(code) }
    }

    /** Resolves the bound type — explicit `boundType` or auto-detected single supertype. */
    private fun resolveBoundType(declaration: KSClassDeclaration, annotation: KSAnnotation): KSType? {
        val explicit = annotation.argument("boundType")
        if (explicit != null && explicit.declaration.qualifiedName?.asString() != "kotlin.Unit") {
            return explicit
        }

        val supertypes = declaration.superTypes
            .map { it.resolve() }
            .filter { it.declaration.qualifiedName?.asString() != "kotlin.Any" }
            .toList()

        return when {
            supertypes.size == 1 -> supertypes.single()
            supertypes.isEmpty() -> {
                logger.error("@ContributesBinding: ${declaration.simpleName.asString()} has no supertypes", declaration)
                null
            }
            else -> {
                logger.error(
                    "@ContributesBinding: ${declaration.simpleName.asString()} has multiple supertypes " +
                        "(${supertypes.joinToString { it.declaration.simpleName.asString() }}). " +
                        "Specify boundType explicitly.",
                    declaration,
                )
                null
            }
        }
    }

    /** Finds a known Dagger scope annotation on the class, if any. */
    private fun findScopeAnnotation(declaration: KSClassDeclaration): String? =
        declaration.annotations
            .map { it.annotationType.resolve().declaration.qualifiedName?.asString() }
            .firstOrNull { it in SCOPE_ANNOTATIONS }

    /** Renders a KSType including type arguments (e.g. `Mapper<FooDTO, Foo>`). */
    private fun renderType(type: KSType): String {
        val base = type.declaration.simpleName.asString()
        val args = type.arguments
        if (args.isEmpty()) return base
        val rendered = args.joinToString(", ") { arg ->
            arg.type?.resolve()?.declaration?.simpleName?.asString() ?: "*"
        }
        return "$base<$rendered>"
    }

    private fun KSAnnotation.argument(name: String): KSType? =
        arguments.firstOrNull { it.name?.asString() == name }?.value as? KSType

    private fun KSAnnotation.isContributesBinding(): Boolean =
        annotationType.resolve().declaration.qualifiedName?.asString() == CONTRIBUTES_BINDING
}
