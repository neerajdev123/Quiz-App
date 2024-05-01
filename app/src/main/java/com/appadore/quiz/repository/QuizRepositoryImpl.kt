package com.appadore.quiz.repository

import com.appadore.quiz.data.StaticQuestionStore
import com.appadore.quiz.model.Question
import com.appadore.quiz.model.QuestionResponse
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val staticDataSource: StaticQuestionStore
) : QuizRepository {

    override fun getQuestions(): List<Question> {
        return staticDataSource.questions
    }
}