package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.emisc0607.expedientecaepsi.MainActivity
import com.emisc0607.expedientecaepsi.R
import com.emisc0607.expedientecaepsi.databinding.FragmentAssistanceBinding
import com.emisc0607.expedientecaepsi.entities.Expediente
import com.emisc0607.expedientecaepsi.entities.SpecificFile
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AssistanceFragment : Fragment() {

    private lateinit var binding: FragmentAssistanceBinding
    private var pathExpedientes = "expedientes"
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference
    private var mActivity: MainActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssistanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity?.hideFab(true)
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    private fun hideKeyboard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun updateArguments(expediente: Expediente) {
        binding.tieKey.setText(expediente.id)
        val ref = expediente.id
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("$pathExpedientes/$ref")
        Log.e("mDatabaseReference", "$mDatabaseReference")
        mDatabaseReference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val file = snapshot.getValue(SpecificFile::class.java)
                file?.let {
                    binding.tieName.setText(it.name)
                    binding.tieAge.setText(it.age)
                    binding.tieCurp.setText(it.imgUrl)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error al leer DB", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
