package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.emisc0607.expedientecaepsi.R
import com.emisc0607.expedientecaepsi.databinding.FragmentHomeBinding
import com.emisc0607.expedientecaepsi.databinding.ItemFileBinding
import com.emisc0607.expedientecaepsi.entities.Expediente
import com.emisc0607.expedientecaepsi.entities.ExpedienteAdapter
import com.emisc0607.expedientecaepsi.entities.ExpedienteProvider
import com.emisc0607.expedientecaepsi.entities.ExpedienteViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

/*
class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mAdapter: ExpedienteAdapter
    private var mutableDbList: MutableList<Expediente> = ExpedienteProvider.dataList.toMutableList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ExpedienteProvider.dataList
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = ExpedienteAdapter(
            dbData = mutableDbList,
            onClickListener = { expediente -> onItemSelected(expediente) },
            onClickDelete = { position -> onDeletedItem(position) })
        mBinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mBinding.recyclerView.adapter = mAdapter

    }

    private fun onItemSelected(expediente: Expediente) {
        Toast.makeText(requireContext(), expediente.name, Toast.LENGTH_SHORT).show()
    }

    private fun onDeletedItem(position: Int) {
        mutableDbList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }
}*/
class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mAdapter: ExpedienteAdapter
    private var mutableDbList: MutableList<Expediente> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("expedientes")
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = ExpedienteAdapter(
            dbData = mutableDbList,
            onClickListener = { expediente -> onItemSelected(expediente) },
            onClickDelete = { position -> onDeletedItem(position) })
        mBinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        mBinding.recyclerView.adapter = mAdapter
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
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", "Error al leer datos de la base de datos: $error")
            }
        })
    }

    private fun onItemSelected(expediente: Expediente) {
        Toast.makeText(requireContext(), expediente.name, Toast.LENGTH_SHORT).show()
    }

    private fun onDeletedItem(position: Int) {
        val expediente = mutableDbList[position]
        databaseReference.child(expediente.id).removeValue()
            .addOnSuccessListener {
                mutableDbList.removeAt(position)
                mAdapter.notifyItemRemoved(position)
            }
            .addOnFailureListener { error ->
                Log.e("HomeFragment", "Error al eliminar expediente: $error")
            }
    }
}
