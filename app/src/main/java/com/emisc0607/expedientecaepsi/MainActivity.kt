package com.emisc0607.expedientecaepsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.emisc0607.expedientecaepsi.databinding.ActivityMainBinding
import com.emisc0607.expedientecaepsi.entities.Expediente
import com.emisc0607.expedientecaepsi.entities.ExpedienteAdapter
import com.emisc0607.expedientecaepsi.entities.MainAux
import com.emisc0607.expedientecaepsi.entities.FragmentListener
import com.emisc0607.expedientecaepsi.fragmentBuilders.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), MainAux, FragmentListener {
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

    //RecyclerView
    private lateinit var mAdapter: ExpedienteAdapter
    private var mutableDbList: MutableList<Expediente> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        launchFragment()
        databaseReference = FirebaseDatabase.getInstance().getReference("expedientes")
        initRecyclerView()
        fetchFiles()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun initRecyclerView() {
        mAdapter = ExpedienteAdapter(
            dbData = mutableDbList,
            onClickListener = { expediente -> onItemSelected(expediente) },
            onClickDelete = { position -> onDeletedItem(position) })
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = mAdapter
        readDataFromDatabase()
    }

    private fun readDataFromDatabase() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (expedienteSnapshot in snapshot.children) {
                    val expediente = expedienteSnapshot.getValue(Expediente::class.java)
                    expediente?.let { mutableDbList.add(it) }
                }
                mAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun onItemSelected(expediente: Expediente) {
        Toast.makeText(this, expediente.name, Toast.LENGTH_SHORT).show()
        assistanceFragment.updateArguments(expediente)
        idFileFragment.updateArguments(expediente)
        historyFragment.updateArguments(expediente)
        launchAssistanceFragment()

    }

    private fun launchAssistanceFragment() {
        binding.tvMain.text = getString(R.string.str_asistencia)
        hideFab()
        mFragmentManager.beginTransaction().hide(mActiveFragment).show(assistanceFragment)
            .commit()
        mActiveFragment = assistanceFragment
    }

    private fun onDeletedItem(position: Int) {
        val expediente = mutableDbList[position]
        databaseReference.child(expediente.id).removeValue()
            .addOnSuccessListener {
                mutableDbList.removeAt(position)
                mAdapter.notifyItemRemoved(position)
                mAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    this,
                    "Error al eliminar expediente: $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun fetchFiles() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mutableDbList.clear()
                for (dataSnapshot in snapshot.children) {
                    val expediente = dataSnapshot.getValue(Expediente::class.java)
                    if (expediente != null) {
                        mutableDbList.add(expediente)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
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
                hideFab(true)
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(homeFragment)
                    .commit()
                mActiveFragment = homeFragment
                true
            }
            R.id.bAssistance -> {
                binding.tvMain.text = getString(R.string.str_asistencia)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(assistanceFragment)
                    .commit()
                mActiveFragment = assistanceFragment
                true
            }
            R.id.bInitialInterview -> {
                binding.tvMain.text = getString(R.string.str_entrevista_inicial)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(interviewFragment)
                    .commit()
                mActiveFragment = interviewFragment
                true
            }
            R.id.bId -> {
                binding.tvMain.text = getString(R.string.str_ficha_id)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(idFileFragment)
                    .commit()
                mActiveFragment = idFileFragment
                true
            }
            R.id.bClinicHistory -> {
                binding.tvMain.text = getString(R.string.str_historia_clinica)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(historyFragment)
                    .commit()
                mActiveFragment = historyFragment
                true
            }
            R.id.bEvaluation -> {
                binding.tvMain.text = getString(R.string.str_evaluacion)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(evaluationFragment)
                    .commit()
                mActiveFragment = evaluationFragment
                true
            }
            R.id.bMapPat -> {
                binding.tvMain.text = getString(R.string.str_patogenesis)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mapPatFragment)
                    .commit()
                mActiveFragment = mapPatFragment
                true
            }
            R.id.bMapGoals -> {
                binding.tvMain.text = getString(R.string.str_metas)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(mapGoalsFragment)
                    .commit()
                mActiveFragment = mapGoalsFragment
                true
            }
            R.id.bTreatment -> {
                binding.tvMain.text = getString(R.string.str_tratamiento)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(treatmentFragment)
                    .commit()
                mActiveFragment = treatmentFragment
                true
            }
            R.id.bEvolution -> {
                binding.tvMain.text = getString(R.string.str_nota_psicologica)
                hideFab()
                mFragmentManager.beginTransaction().hide(mActiveFragment).show(evolutionFragment)
                    .commit()
                mActiveFragment = evolutionFragment
                true
            }
            R.id.bIntervention -> {
                binding.tvMain.text = getString(R.string.str_hoja_intervencion)
                hideFab()
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

    /*
    * MainAux
    * */
    override fun hideFab(isVisible: Boolean) {
        if (isVisible) {
            binding.fab.show()
            binding.recyclerView.visibility = View.VISIBLE
        }
        else {
            binding.fab.hide()
            binding.recyclerView.visibility = View.GONE
        }
    }

    override fun onMyFragmentArgumentsReceived(arg1: Expediente) {
        assistanceFragment.updateArguments(arg1)
    }
}
