package com.datienza.spacex.hiltbinder

import kotlin.reflect.KClass

/**
 * Anvil-style annotation that auto-generates a Hilt `@Module` with a `@Binds` method.
 *
 * ```
 * @ContributesBinding(SingletonComponent::class)
 * @Singleton
 * class RepositoryImpl @Inject constructor(…) : Repository
 * ```
 *
 * Generates:
 * ```
 * @Module
 * @InstallIn(SingletonComponent::class)
 * interface RepositoryImpl_HiltBinderModule {
 *     @Binds @Singleton
 *     fun bind(impl: RepositoryImpl): Repository
 * }
 * ```
 *
 * @param scope the Hilt component to install in (e.g. `SingletonComponent::class`).
 * @param boundType explicit supertype to bind to. When [Unit], auto-detected from the single
 *   direct supertype (excluding `Any`). Specify explicitly when the class has multiple supertypes.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContributesBinding(
    val scope: KClass<*>,
    val boundType: KClass<*> = Unit::class,
)
