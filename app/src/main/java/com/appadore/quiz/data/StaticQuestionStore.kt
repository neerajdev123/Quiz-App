package com.appadore.quiz.data

import com.appadore.quiz.data.mapper.Mapper
import com.appadore.quiz.model.Question
import com.appadore.quiz.model.QuestionEntity
import com.appadore.quiz.model.QuestionResponse

class StaticQuestionStore (
    private val response: QuestionResponse,
    private val mapper: Mapper<QuestionEntity, Question>
) {

    val questions = getMappedResponse()

    private fun getMappedResponse(): List<Question> {
        val questions = mutableListOf<Question>()
        response.questions.map {
            val question = mapper.map(it)
            questions.add(question)
        }
        return questions
    }


}