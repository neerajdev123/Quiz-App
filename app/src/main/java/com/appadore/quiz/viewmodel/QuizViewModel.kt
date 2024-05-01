package com.appadore.quiz.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appadore.quiz.data.StaticQuestionStore
import com.appadore.quiz.model.Question
import com.appadore.quiz.repository.QuizRepository
import com.appadore.quiz.repository.QuizRepositoryImpl
import java.util.Calendar


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

    private val _quizStartCountDown = MutableLiveData("")
    val quizStartCountDown : LiveData<String> = _quizStartCountDown

    private val _navigateToQuiz = MutableLiveData(false)
    val navigateToQuiz : LiveData<Boolean> = _navigateToQuiz

    private var quizRepository : QuizRepository? = null

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
        quizRepository = QuizRepositoryImpl()
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
        _navigateToQuiz.value = false

        getQuestions()

    }

    fun resetForNavigation(){
        _navigateToQuiz.value = false
    }

    //set countdown timer, can be done in view as well, but handling here helps in orientation change
    fun startTimer(){
        timer.start()
    }

    fun stopTimer(){
        timer.cancel()
    }

    //invoked from home screen to set timer for quiz
    fun setQuizStartTime(hour : Int, min : Int){
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.clear(Calendar.SECOND)
        val timeLong = calendar.timeInMillis
        val currentTime = System.currentTimeMillis()
        val countDownTime = timeLong - currentTime

        val timer = object: CountDownTimer(countDownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsValue = millisUntilFinished / 1000
                val hours = secondsValue / 3600
                val minutes = (secondsValue % 3600) / 60
                val seconds = secondsValue % 60

                val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                _quizStartCountDown.value = "Time Remaining for quiz : $time"
            }

            override fun onFinish() {
                _navigateToQuiz.value = true
            }
        }
        timer.start()
    }

    private fun getQuestions(){
        _questions.value = quizRepository?.getQuestions()
    }

}