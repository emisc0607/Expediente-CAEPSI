package com.emisc0607.expedientecaepsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.emisc0607.expedientecaepsi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.bAssistance -> {
                binding.tvMain.text = getString(R.string.str_asistencia)
                true
            }
            R.id.bInitialInterview -> {
                binding.tvMain.text = getString(R.string.str_entrevista_inicial)
                true
            }
            R.id.bId -> {
                binding.tvMain.text = getString(R.string.str_ficha_id)
                true
            }
            R.id.bClinicHistory -> {
                binding.tvMain.text = getString(R.string.str_historia_clinica)
                true
            }
            R.id.bEvaluation -> {
                binding.tvMain.text = getString(R.string.str_evaluacion)
                true
            }
            R.id.bMapPat -> {
                binding.tvMain.text = getString(R.string.str_patogenesis)
                true
            }
            R.id.bMapGoals -> {
                binding.tvMain.text = getString(R.string.str_metas)
                true
            }
            R.id.bTreatment -> {
                binding.tvMain.text = getString(R.string.str_tratamiento)
                true
            }
            R.id.bEvolution -> {
                binding.tvMain.text = getString(R.string.str_nota_psicologica)
                true
            }
            R.id.bIntervention -> {
                binding.tvMain.text = getString(R.string.str_hoja_intervencion)
                true
            }

            R.id.bHelp -> {
                showHelp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showHelp() {
        Toast.makeText(this, "Aiudaaaaa", Toast.LENGTH_SHORT).show()
    }
}