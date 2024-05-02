package com.appadore.quiz.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.appadore.quiz.R
import com.appadore.quiz.databinding.FragmentQuizBinding
import com.appadore.quiz.extensions.show
import com.appadore.quiz.utils.TimerTime
import com.appadore.quiz.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var binding : FragmentQuizBinding? = null
    private val sharedViewModel: QuizViewModel by activityViewModels()
    private var totalQuestions = 0
    private var selectedAnswer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentQuizBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        sharedViewModel.getCurrentQuestion()

        sharedViewModel.questions.observe(viewLifecycleOwner) {
            totalQuestions = it?.size ?: 0
            sharedViewModel.getCurrentQuestion()
        }

        sharedViewModel.currentQuestion.observe(viewLifecycleOwner) {
            binding?.apply {
                this.txtQn.text = it?.question
                this.imgFlag.load("https://flagsapi.com/${it?.countryCode}/flat/64.png")

                handleOptionBg(1, -1)
                handleOptionBg(2, -1)
                handleOptionBg(3, -1)
                handleOptionBg(4, -1)

                this.btnOption1.text = it?.option1
                this.btnOption2.text = it?.option2
                this.btnOption3.text = it?.option3
                this.btnOption4.text = it?.option4
            }
        }

        sharedViewModel.questionNumber.observe(viewLifecycleOwner) {
            val delay = if(it == 1) 0L else TimerTime.QUESTION_TIME_GAP
            Handler(Looper.getMainLooper()).postDelayed({
                binding?.txtQnNo?.text = "$it / $totalQuestions"
                sharedViewModel.startTimer()
            }, delay)
        }

        sharedViewModel.userSelectedAnswer.observe(viewLifecycleOwner){
            selectedAnswer = it
            handleOptionBg(it, 1)
        }

        sharedViewModel.correctAnswer.observe(viewLifecycleOwner){
            if(it != null) {
                sharedViewModel.stopTimer()
                if (it == selectedAnswer) {
                    sharedViewModel.saveQuestionAnswer(true)
                    handleOptionBg(it, 2)
                } else {
                    sharedViewModel.saveQuestionAnswer(false)
                    handleOptionBg(selectedAnswer, 3)
                    handleOptionBg(it, 2)
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    sharedViewModel.getCurrentQuestion()
                }, TimerTime.QUESTION_TIME_GAP)
            }
        }

        sharedViewModel.countDownTime.observe(viewLifecycleOwner){
            binding?.txtTimer?.text = it
        }

        sharedViewModel.lastQuestion.observe(viewLifecycleOwner){
            if(it){
                binding?.btnSubmit?.show(true)
            }
        }

        sharedViewModel.finalAnswer.observe(viewLifecycleOwner){
            sharedViewModel.resetForNavigation()
            findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
        }

        binding?.btnOption1?.setOnClickListener{
            sharedViewModel.checkAnswer(1)
        }

        binding?.btnOption2?.setOnClickListener{
            sharedViewModel.checkAnswer(2)
        }

        binding?.btnOption3?.setOnClickListener{
            sharedViewModel.checkAnswer(3)
        }

        binding?.btnOption4?.setOnClickListener{
            sharedViewModel.checkAnswer(4)
        }

        binding?.btnSubmit?.setOnClickListener {
            sharedViewModel.submitAnswers()
        }
    }

    private fun handleOptionBg(option : Int, mode : Int = 1){
        when(option){
            1 -> {
                setBg(mode, binding?.btnOption1!!)
            }

            2 -> {
                setBg(mode, binding?.btnOption2!!)
            }

            3 -> {
                setBg(mode, binding?.btnOption3!!)
            }

            4 -> {
                setBg(mode, binding?.btnOption4!!)
            }
        }
    }

    private fun setBg(mode : Int, view : AppCompatButton){
        when (mode) {
            1 -> {
                view.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.bg_option_select, null))
            }
            2 -> {
                view.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.bg_option_correct, null))
            }
            3 -> {
                view.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.bg_option_wrong, null))
            }
            else -> {
               view.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,
                   R.drawable.bg_option_normal, null))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}