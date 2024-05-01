package com.appadore.quiz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.appadore.quiz.R
import com.appadore.quiz.databinding.FragmentHomeBinding
import com.appadore.quiz.viewmodel.QuizViewModel


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
        binding?.btnStart?.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        sharedViewModel.finalAnswer.observe(viewLifecycleOwner){
            if(it != null){
                binding?.layoutScore?.visibility = View.VISIBLE
                binding?.btnStart?.visibility = View.GONE
                binding?.txtScore?.text = it
            }else{
                binding?.btnStart?.visibility = View.VISIBLE
                binding?.layoutScore?.visibility = View.GONE
            }
        }

        binding?.btnRetake?.setOnClickListener {
            binding?.btnStart?.visibility = View.VISIBLE
            binding?.layoutScore?.visibility = View.GONE
            sharedViewModel.reset()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}