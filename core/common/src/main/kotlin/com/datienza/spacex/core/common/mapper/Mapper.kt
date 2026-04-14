package com.datienza.spacex.core.common.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
