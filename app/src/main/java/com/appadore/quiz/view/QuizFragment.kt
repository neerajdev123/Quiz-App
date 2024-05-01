package com.appadore.quiz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.appadore.quiz.R
import com.appadore.quiz.databinding.FragmentHomeBinding
import com.appadore.quiz.databinding.FragmentQuizBinding
import com.appadore.quiz.viewmodel.QuizViewModel

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
                this.btnOption1.text = it?.option1
                this.btnOption2.text = it?.option2
                this.btnOption3.text = it?.option3
                this.btnOption4.text = it?.option4
            }
        }

        sharedViewModel.questionNumber.observe(viewLifecycleOwner) {
            binding?.txtQnNo?.text = "$it / $totalQuestions"
        }

        sharedViewModel.userSelectedAnswer.observe(viewLifecycleOwner){
            selectedAnswer = it
        }

        sharedViewModel.correctAnswer.observe(viewLifecycleOwner){
            if(it != null) {
                if (it == selectedAnswer) {
                    Toast.makeText(activity, "Correct", Toast.LENGTH_SHORT).show()
                    sharedViewModel.saveQuestionAnswer(true)
                } else {
                    sharedViewModel.saveQuestionAnswer(false)
                }
                sharedViewModel.getCurrentQuestion()
            }
        }

        sharedViewModel.lastQuestion.observe(viewLifecycleOwner){
            if(it){
                binding?.btnSubmit?.visibility = View.VISIBLE
            }
        }

        sharedViewModel.finalAnswer.observe(viewLifecycleOwner){
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}