package com.appadore.quiz.data.mapper

import com.appadore.quiz.model.Question
import com.appadore.quiz.model.QuestionEntity

class QuestionResponseMapper : Mapper<QuestionEntity, Question> {
    override fun map(data: QuestionEntity): Question {
        val correctCountry = data.countries.filter { country ->
            data.answerId == country.id
        }[0]
        val correctOption = data.countries.indexOf(correctCountry) + 1
        return Question(
            "Guess the Country by the Flag",
            data.countries[0].name,
            data.countries[1].name,
            data.countries[2].name,
            data.countries[3].name,
            correctOption = correctOption,
            countryCode = data.countryCode
        )
    }
}