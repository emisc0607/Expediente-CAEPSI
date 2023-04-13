package com.emisc0607.expedientecaepsi.entities

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.emisc0607.expedientecaepsi.databinding.ItemFileBinding

class ExpedienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemFileBinding.bind(view)
    fun render(
        expediente: Expediente,
        onClickListener: (Expediente) -> Unit,
        onClickDelete: (Int) -> Unit
    ) {
        binding.tvName.text = expediente.name
        Glide
            .with(binding.imagePhoto.context)
            .load(expediente.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop().into(binding.imagePhoto)
        itemView.setOnClickListener {
            onClickListener(expediente)
        }
        binding.bDelete.setOnClickListener {
            onClickDelete(adapterPosition)
        }
    }
}