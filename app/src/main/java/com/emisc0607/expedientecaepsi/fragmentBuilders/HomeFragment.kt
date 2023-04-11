package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emisc0607.expedientecaepsi.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etUser.text?.let {
            binding.etPassword.text?.let {
                buttons()
            }
        }
    }

    private fun buttons() {
        binding.bLogin.setOnClickListener {

            if (binding.etUser.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()) {
                Snackbar.make(binding.root, "Iniciando sesión", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Error iniciando sesión", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.googleButton.setOnClickListener {
            if (binding.etUser.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()) {
                Snackbar.make(binding.root, "Iniciando sesión con Google", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Error iniciando sesión", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}