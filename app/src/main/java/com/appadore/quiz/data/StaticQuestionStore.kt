package com.appadore.quiz.data

import com.appadore.quiz.data.mapper.QuestionResponseMapper
import com.appadore.quiz.model.Question
import com.appadore.quiz.model.QuestionResponse
import javax.inject.Inject

class StaticQuestionStore @Inject constructor(private val response: QuestionResponse) {

    private val mapper = QuestionResponseMapper()
    val questions = getMappedResponse()

    private fun getMappedResponse() : List<Question> {
        val questions = mutableListOf<Question>()
        response.questions.map {
            val question = mapper.map(it)
            questions.add(question)
        }
        return questions
    }


}