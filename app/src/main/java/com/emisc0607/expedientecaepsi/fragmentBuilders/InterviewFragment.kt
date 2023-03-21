package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emisc0607.expedientecaepsi.databinding.FragmentInterviewBinding

class InterviewFragment : Fragment() {
    private lateinit var binding: FragmentInterviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInterviewBinding.inflate(inflater, container, false)
        return binding.root
    }

}