package com.emisc0607.expedientecaepsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.emisc0607.expedientecaepsi.databinding.ActivityMainBinding
import com.emisc0607.expedientecaepsi.fragmentBuilders.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //    Fragments
    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager

    private val homeFragment = HomeFragment()
    private val assistanceFragment = AssistanceFragment()
    private val idFileFragment = IdFileFragment()
    private val interviewFragment = InterviewFragment()
    private val historyFragment = ClinicHistoryFragment()
    private val evaluationFragment = EvaluationFragment()
    private val mapPatFragment = MapPatFragment()
    private val mapGoalsFragment = MapGoalsFragment()
    private val treatmentFragment = TreatmentFragment()
    private val evolutionFragment = EvolutionFragment()
    private val interventionFragment = InterventionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        launchFragment()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun launchFragment() {
        mFragmentManager = supportFragmentManager
        mActiveFragment = homeFragment
//        Control de asistencia
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, assistanceFragment, AssistanceFragment::class.java.name)
            .hide(assistanceFragment)
            .commit()
//        Ficha de identificacion
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, idFileFragment, IdFileFragment::class.java.name)
            .hide(idFileFragment)
            .commit()
//        Entrevista inicial
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, interviewFragment, InterviewFragment::class.java.name)
            .hide(interviewFragment)
            .commit()
//        Historia clinica
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, historyFragment, ClinicHistoryFragment::class.java.name)
            .hide(historyFragment)
            .commit()
//        Proceso de evaluacion
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, evaluationFragment, EvaluationFragment::class.java.name)
            .hide(evaluationFragment)
            .commit()
//        Mapa de patogenesis
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, mapPatFragment, MapPatFragment::class.java.name)
            .hide(mapPatFragment)
            .commit()
//        Mapa de alcance de metas
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, mapGoalsFragment, MapGoalsFragment::class.java.name)
            .hide(mapGoalsFragment)
            .commit()
//        Plan de tratamiento
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, treatmentFragment, TreatmentFragment::class.java.name)
            .hide(treatmentFragment)
            .commit()
//        Nota de evolucion
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, evolutionFragment, EvolutionFragment::class.java.name)
            .hide(evolutionFragment)
            .commit()
//        Hoja de intervencion en crisis
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, interventionFragment, InterventionFragment::class.java.name)
            .hide(interventionFragment)
            .commit()
//        Home Fragment
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, homeFragment, HomeFragment::class.java.name).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.bFiles -> {
                binding.tvMain.text = getString(R.string.str_welcome)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(homeFragment)
                    .commit()
                mActiveFragment = homeFragment
                true
            }
            R.id.bAssistance -> {
                binding.tvMain.text = getString(R.string.str_asistencia)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(assistanceFragment)
                    .commit()
                mActiveFragment = assistanceFragment
                true
            }
            R.id.bInitialInterview -> {
                binding.tvMain.text = getString(R.string.str_entrevista_inicial)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(interviewFragment)
                    .commit()
                mActiveFragment = interviewFragment
                true
            }
            R.id.bId -> {
                binding.tvMain.text = getString(R.string.str_ficha_id)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(idFileFragment)
                    .commit()
                mActiveFragment = idFileFragment
                true
            }
            R.id.bClinicHistory -> {
                binding.tvMain.text = getString(R.string.str_historia_clinica)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(historyFragment)
                    .commit()
                mActiveFragment = historyFragment
                true
            }
            R.id.bEvaluation -> {
                binding.tvMain.text = getString(R.string.str_evaluacion)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(evaluationFragment)
                    .commit()
                mActiveFragment = evaluationFragment
                true
            }
            R.id.bMapPat -> {
                binding.tvMain.text = getString(R.string.str_patogenesis)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mapPatFragment)
                    .commit()
                mActiveFragment = mapPatFragment
                true
            }
            R.id.bMapGoals -> {
                binding.tvMain.text = getString(R.string.str_metas)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mapGoalsFragment)
                    .commit()
                mActiveFragment = mapGoalsFragment
                true
            }
            R.id.bTreatment -> {
                binding.tvMain.text = getString(R.string.str_tratamiento)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(treatmentFragment)
                    .commit()
                mActiveFragment = treatmentFragment
                true
            }
            R.id.bEvolution -> {
                binding.tvMain.text = getString(R.string.str_nota_psicologica)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(evolutionFragment)
                    .commit()
                mActiveFragment = evolutionFragment
                true
            }
            R.id.bIntervention -> {
                binding.tvMain.text = getString(R.string.str_hoja_intervencion)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(interventionFragment)
                    .commit()
                mActiveFragment = interventionFragment
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