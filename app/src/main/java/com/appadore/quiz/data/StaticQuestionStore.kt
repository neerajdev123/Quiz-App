package com.appadore.quiz.data

import com.appadore.quiz.model.Question
import com.appadore.quiz.model.QuestionResponse
import javax.inject.Inject

class StaticQuestionStore @Inject constructor(val response: QuestionResponse) {

    val questions = getMappedResponse()

    private fun getMappedResponse() : List<Question> {
        val questions = mutableListOf<Question>()
        response.questions.map {
            val correctCountry = it.countries.filter { country ->
                it.answerId == country.id
            } [0]
            val correctOption = it.countries.indexOf(correctCountry) + 1
            val question = Question("Guess the Country by the Flag",
                it.countries[0].name,
                it.countries[1].name,
                it.countries[2].name,
                it.countries[3].name,
                correctOption = correctOption,
                countryCode = it.countryCode
                )
            questions.add(question)
        }
        return questions
    }


}