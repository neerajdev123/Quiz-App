package com.appadore.quiz.repository

import com.appadore.quiz.data.StaticQuestionStore
import com.appadore.quiz.model.Question

class QuizRepositoryImpl : QuizRepository {
    override fun getQuestions(): List<Question> {
        return StaticQuestionStore().questions
    }
}