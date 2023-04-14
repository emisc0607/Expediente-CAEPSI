package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emisc0607.expedientecaepsi.databinding.FragmentClinicHistoryBinding
import com.emisc0607.expedientecaepsi.entities.Expediente

class ClinicHistoryFragment : Fragment() {
    private lateinit var binding: FragmentClinicHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClinicHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun updateArguments(expediente: Expediente) {
        binding.tieName.setText(expediente.name)
        binding.tieKey.setText(expediente.id)
    }
}