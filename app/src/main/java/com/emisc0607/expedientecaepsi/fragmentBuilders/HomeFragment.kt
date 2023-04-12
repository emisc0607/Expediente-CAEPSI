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
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<Expediente, ExpedienteHolder>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = FirebaseDatabase.getInstance().reference.child("expedientes")
        val options =
            FirebaseRecyclerOptions.Builder<Expediente>().setQuery(query, Expediente::class.java)
                .build()

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Expediente, ExpedienteHolder>(options) {

            private lateinit var mContext: Context

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpedienteHolder {
                mContext = parent.context
                val view =
                    LayoutInflater.from(mContext).inflate(R.layout.item_file, parent, false)
                return ExpedienteHolder(view)
            }

            override fun onBindViewHolder(
                holder: ExpedienteHolder,
                position: Int,
                model: Expediente
            ) {
                val expediente = getItem(position)
                with(holder) {
                    setListener(expediente)
                    binding.tvName.text = expediente.id
                    Glide
                        .with(mContext)
                        .load(expediente.imgUrl)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imagePhoto)
                }
            }

            override fun onDataChanged() {
                super.onDataChanged()
                mBinding.progressBar.visibility = View.GONE
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Toast.makeText(mContext, error.message, Toast.LENGTH_SHORT).show()

            }
        }
        mLayoutManager = GridLayoutManager(context, 2)
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mFirebaseAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()
    }

    inner class ExpedienteHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFileBinding.bind(view)
        fun setListener(file: Expediente) {

        }
    }
    /*private fun buttons() {
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
    }*/
}