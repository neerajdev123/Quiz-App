package com.appadore.quiz.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appadore.quiz.data.StaticQuestionStore
import com.appadore.quiz.model.Question

class QuizViewModel : ViewModel() {

    private val _questions = MutableLiveData<List<Question?>?>()
    val questions: LiveData<List<Question?>?> = _questions

    private val _currentQuestion = MutableLiveData<Question?>()
    val currentQuestion: LiveData<Question?> = _currentQuestion

    private val _userSelectedAnswer = MutableLiveData<Int>(-1)
    val userSelectedAnswer: LiveData<Int> = _userSelectedAnswer

    private val _correctAnswer = MutableLiveData<Int?>(null)
    val correctAnswer: LiveData<Int?> = _correctAnswer

    private val _finalAnswer = MutableLiveData<String?>()
    val finalAnswer: LiveData<String?> = _finalAnswer

    private val _lastQuestion = MutableLiveData<Boolean>()
    val lastQuestion: LiveData<Boolean> = _lastQuestion

    private val _questionNumber = MutableLiveData(1)
    val questionNumber: LiveData<Int> = _questionNumber

    private val _countDownTime = MutableLiveData("")
    val countDownTime : LiveData<String> = _countDownTime

    private var correctAnswers = 0

    private val timer = object: CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _countDownTime.value = (millisUntilFinished/1000L).toString()
        }

        override fun onFinish() {
            checkAnswer(0)
        }
    }

    //fetch questions on view model init
    init{
        getQuestions()
    }

    //get current question, managed at view model to manage orientation change
    fun getCurrentQuestion(){
        if(_questionNumber.value!! <= _questions.value?.size!!){
            _currentQuestion.value = _questions.value?.get(_questionNumber.value!! - 1)!!
        }
        _userSelectedAnswer.value = 0
        _correctAnswer.value = null
    }

    //save answer to increment question no
    fun saveQuestionAnswer(isCorrect : Boolean){
        if(isCorrect) {
            ++correctAnswers
        }
        if(_questionNumber.value == _questions.value?.size!!){
            _lastQuestion.value = true
        }else {
            _questionNumber.value = _questionNumber.value!! + 1
        }
    }

    //actions to be performed when user selects an option
    fun checkAnswer(option : Int) {
        _userSelectedAnswer.value = option
        _correctAnswer.value = _questions.value?.get(_questionNumber.value!! - 1)!!.correctOption
    }

    //click submit on questions complete
    fun submitAnswers() {
        if(lastQuestion.value == true){
            _finalAnswer.value = "You scored $correctAnswers / ${_questions.value?.size}"
        }
    }

    //reset all values to enable retake/fresh take
    fun reset(){
        _questions.value = null
        _currentQuestion.value = null
        _userSelectedAnswer.value = 0
        _correctAnswer.value = 0
        _finalAnswer.value = null
        _lastQuestion.value = false

        getQuestions()

    }

    //set countdown timer, can be done in view as well, but handling here helps in orientation change
    fun startTimer(){
        timer.start()
    }

    fun stopTimer(){
        timer.cancel()
    }

    private fun getQuestions(){
        _questions.value = StaticQuestionStore().questions
    }

}