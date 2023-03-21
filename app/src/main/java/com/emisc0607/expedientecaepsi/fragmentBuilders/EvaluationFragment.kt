package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emisc0607.expedientecaepsi.databinding.FragmentEvaluationBinding

class EvaluationFragment : Fragment() {
    private lateinit var binding: FragmentEvaluationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvaluationBinding.inflate(inflater, container, false)
        return binding.root
    }
}