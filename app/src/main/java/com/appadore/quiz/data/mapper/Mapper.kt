package com.appadore.quiz.data.mapper

interface Mapper<S, D> {
    fun map(data: S): D
}