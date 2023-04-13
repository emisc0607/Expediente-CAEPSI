package com.emisc0607.expedientecaepsi.fragmentBuilders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.emisc0607.expedientecaepsi.MainActivity
import com.emisc0607.expedientecaepsi.R
import com.emisc0607.expedientecaepsi.databinding.FragmentAssistanceBinding
import com.emisc0607.expedientecaepsi.entities.Expediente
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
        mStorageReference = FirebaseStorage.getInstance().reference
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(pathExpedientes)
        binding.rgAssistance.setOnCheckedChangeListener { _, checkedId ->
            val key = mDatabaseReference.push().key!!
            if (checkedId == R.id.rbYes) {
                /*saveData(
                    key,
                    binding.tieName.text.toString(),
                    binding.tieAge.text.toString(),
                    binding.tieKey.text.toString()
                )*/
            }
        }
    }

    private fun saveData(key: String, name: String, age: String, curp: String) {
        val file = Expediente(id = key, name = name, age = age)
        mDatabaseReference.child(key).setValue(file)

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
}
