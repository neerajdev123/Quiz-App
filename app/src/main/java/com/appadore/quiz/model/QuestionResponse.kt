package com.appadore.quiz.model

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("questions")
    val questions : List<QuestionEntity>
)
