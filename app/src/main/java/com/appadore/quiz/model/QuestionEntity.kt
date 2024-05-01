package com.appadore.quiz.model

import com.google.gson.annotations.SerializedName

data class QuestionEntity(
    @SerializedName("countries")
    val countries : List<Country>,
    @SerializedName("answer_id")
    val answerId : Int,
    @SerializedName("country_code")
    val countryCode : String
)
