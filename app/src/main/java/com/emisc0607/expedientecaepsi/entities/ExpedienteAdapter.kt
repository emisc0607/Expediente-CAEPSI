package com.emisc0607.expedientecaepsi.entities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emisc0607.expedientecaepsi.R

class ExpedienteAdapter(
    private val dbData: List<Expediente>,
    private val onClickListener: (Expediente) -> Unit,
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<ExpedienteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpedienteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExpedienteViewHolder(layoutInflater.inflate(R.layout.item_file, parent, false))
    }

    override fun getItemCount(): Int = dbData.size

    override fun onBindViewHolder(holder: ExpedienteViewHolder, position: Int) {
        val item = dbData[position]
        holder.render(item, onClickListener, onClickDelete)
    }

}