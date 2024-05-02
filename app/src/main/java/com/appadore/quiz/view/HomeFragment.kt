package com.appadore.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.appadore.quiz.R
import com.appadore.quiz.databinding.FragmentHomeBinding
import com.appadore.quiz.extensions.show
import com.appadore.quiz.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding : FragmentHomeBinding? = null
    private val sharedViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding?.btnSave?.setOnClickListener{
            val hour = binding?.tpTime?.hour
            val min = binding?.tpTime?.minute
            sharedViewModel.setQuizStartTime(hour!!, min!!)
            binding?.btnSave?.show(false)
            binding?.layoutScore?.show(false)
            binding?.txtPickLabel?.show(false)
            binding?.tpTime?.show(false)
        }

        sharedViewModel.navigateToQuiz.observe(viewLifecycleOwner){
            if(it) {
                binding?.layoutScore?.show(false)
                binding?.btnSave?.show(false)
                binding?.txtPickLabel?.show(false)
                binding?.tpTime?.show(false)
                this.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
            }
        }

        sharedViewModel.quizStartCountDown.observe(viewLifecycleOwner){
            binding?.txtTimeRemaining?.show(true)
            binding?.txtTimeRemaining?.text = it
        }

        sharedViewModel.finalAnswer.observe(viewLifecycleOwner){
            if(it != null){
                binding?.layoutScore?.show(true)
                binding?.btnSave?.show(false)
                binding?.txtPickLabel?.show(false)
                binding?.tpTime?.show(false)
                binding?.txtTimeRemaining?.show(false)
                binding?.txtScore?.text = it
            }else{
                binding?.btnSave?.show(true)
                binding?.txtPickLabel?.show(true)
                binding?.tpTime?.show(true)
                binding?.layoutScore?.show(false)
            }
        }

        binding?.btnExit?.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}