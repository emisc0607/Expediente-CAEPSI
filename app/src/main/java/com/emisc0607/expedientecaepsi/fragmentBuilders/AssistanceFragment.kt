package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emisc0607.expedientecaepsi.databinding.FragmentAssistanceBinding

class AssistanceFragment : Fragment() {

    private lateinit var binding: FragmentAssistanceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssistanceBinding.inflate(inflater, container, false)
        return binding.root
    }
}