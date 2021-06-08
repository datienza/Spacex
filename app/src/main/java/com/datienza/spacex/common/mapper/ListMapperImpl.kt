package com.datienza.spacex.common.mapper

import com.datienza.spacex.common.mapper.Mapper

class ListMapperImpl<I, O>(private val mapper: Mapper<I, O>) : ListMapper<I, O> {

    override fun map(input: List<I>): List<O> {
        return input.map { item -> mapper.map(item) }
    }
}