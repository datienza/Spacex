package com.datienza.spacex.common.mapper

interface Mapper<I, O> {

    fun map(input: I): O

}