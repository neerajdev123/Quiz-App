package com.appadore.quiz.data

import com.appadore.quiz.model.Question

class StaticQuestionStore {

    val questions = getDummyQuestions()

    private fun getDummyQuestions() : List<Question>{

        val questions = mutableListOf<Question>()

        questions.add(Question(1, "Identify the flag1", "A", "B", "C", "D", 1))
        questions.add(Question(2, "Identify the flag2", "A", "B", "C", "D", 2))
        questions.add(Question(3, "Identify the flag3", "A", "B", "C", "D", 3))
        questions.add(Question(4, "Identify the flag4", "A", "B", "C", "D", 4))
        questions.add(Question(5, "Identify the flag5", "A", "B", "C", "D", 1))
        questions.add(Question(6, "Identify the flag6", "A", "B", "C", "D", 2))

        return questions
    }
}