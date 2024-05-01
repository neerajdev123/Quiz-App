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
            binding?.btnSave?.visibility = View.GONE
            binding?.layoutScore?.visibility = View.GONE
            binding?.txtPickLabel?.visibility = View.GONE
            binding?.tpTime?.visibility = View.GONE
        }

        sharedViewModel.navigateToQuiz.observe(viewLifecycleOwner){
            if(it) {
                binding?.layoutScore?.visibility = View.GONE
                binding?.btnSave?.visibility = View.GONE
                binding?.txtPickLabel?.visibility = View.GONE
                binding?.tpTime?.visibility = View.GONE
                this.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
            }
        }

        sharedViewModel.quizStartCountDown.observe(viewLifecycleOwner){
            binding?.txtTimeRemaining?.visibility = View.VISIBLE
            binding?.txtTimeRemaining?.text = it
        }

        sharedViewModel.finalAnswer.observe(viewLifecycleOwner){
            if(it != null){
                binding?.layoutScore?.visibility = View.VISIBLE
                binding?.btnSave?.visibility = View.GONE
                binding?.txtPickLabel?.visibility = View.GONE
                binding?.tpTime?.visibility = View.GONE
                binding?.txtTimeRemaining?.visibility = View.GONE
                binding?.txtScore?.text = it
            }else{
                binding?.btnSave?.visibility = View.VISIBLE
                binding?.txtPickLabel?.visibility = View.VISIBLE
                binding?.tpTime?.visibility = View.VISIBLE
                binding?.layoutScore?.visibility = View.GONE
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