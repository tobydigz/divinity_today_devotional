package com.digzdigital.divinitytoday.data.commons


interface Mapper<F, T> {

    fun map1(from: F): T

    fun mapMany(fromCollection: Collection<F>): Collection<T>

}