package com.datienza.spacex.core.network.apiresult

import com.slack.eithernet.ApiResult
import com.slack.eithernet.tag
import okhttp3.Request
import okhttp3.Response
import kotlin.reflect.KClass

/**
 * Maps the value of an [ApiResult.Success]
 */
fun <Source : Any, Target : Any, Error : Throwable> ApiResult<Source, Error>.map(
    mapper: (ApiResult<Source, Error>).(Source) -> Target,
): ApiResult<Target, Error> {
    return when (this) {
        is ApiResult.Failure -> this
        is ApiResult.Success -> ApiResult.success(mapper(value)).copyTagsFrom(this)
    }
}

/**
 * Flat maps an [ApiResult] to another [ApiResult]
 */
fun <SourceApiResult : ApiResult<*, *>, Target : Any, TargetError : Throwable> SourceApiResult.flatMap(
    mapper: (SourceApiResult) -> ApiResult<Target, TargetError>,
): ApiResult<Target, TargetError> {
    return mapper(this)
        .copyTagsFrom(this)
}

private fun <T : Any, E : Any> ApiResult<T, E>.copyTagsFrom(sourceApiResult: ApiResult<*, *>): ApiResult<T, E> {
    val tags: Map<KClass<*>, Any> = buildMap {
        sourceApiResult.tag<Request>()?.let { put(Request::class, it) }
        sourceApiResult.tag<Response>()?.let { put(Response::class, it) }
    }
    return when (this) {
        is ApiResult.Failure.ApiFailure -> withTags(tags)
        is ApiResult.Failure.HttpFailure -> withTags(tags)
        is ApiResult.Failure.NetworkFailure -> withTags(tags)
        is ApiResult.Failure.UnknownFailure -> withTags(tags)
        is ApiResult.Success -> withTags(tags)
    }
}

/**
 * Returns true if ApiResult.Failure was due to an HttpError matching the given error codes.
 * Returns false otherwise.
 */
fun <S : Any, T : Any> ApiResult<S, T>.isHttpErrorCode(vararg httpCodes: Int): Boolean {
    return isHttpErrorCode(httpCodes.toList())
}

fun <S : Any, T : Any> ApiResult<S, T>.isHttpErrorCode(httpCodes: List<Int>): Boolean {
    return this is ApiResult.Failure.HttpFailure<*> && httpCodes.contains(code)
}

/**
 * Inverse of [isHttpErrorCode]
 */
fun <S : Any, T : Any> ApiResult<S, T>.isNotHttpErrorCode(vararg httpCodes: Int): Boolean {
    return isNotHttpErrorCode(httpCodes.toList())
}

fun <S : Any, T : Any> ApiResult<S, T>.isNotHttpErrorCode(httpCodes: List<Int>): Boolean {
    return !isHttpErrorCode(httpCodes)
}

/**
 * Retrieve a header value from the response.
 */
fun <S : Any, T : Any> ApiResult<S, T>.getHeader(header: String): String? =
    tag(Response::class)?.header(header)
