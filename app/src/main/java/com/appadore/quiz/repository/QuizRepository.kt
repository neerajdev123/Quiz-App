package com.appadore.quiz.repository

import com.appadore.quiz.model.Question

interface QuizRepository {

    fun getQuestions() : List<Question>
}