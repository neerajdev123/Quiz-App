package com.appadore.quiz.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country_name")
    val name : String,

    @SerializedName("id")
    val id : Int

)
