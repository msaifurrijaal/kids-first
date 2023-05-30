package com.kelompokpam.kidsfirst.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompokpam.kidsfirst.data.model.Artikel
import com.kelompokpam.kidsfirst.databinding.LayoutItemArtikelBinding

class ArtikelAdapter: RecyclerView.Adapter<ArtikelAdapter.ViewHolder>() {

    private var artikelList = ArrayList<Artikel>()

    fun setArtikelList(newArtikelList: List<Artikel>) {
        artikelList.clear()
        artikelList.addAll(newArtikelList)
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: LayoutItemArtikelBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutItemArtikelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = artikelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var artikel = artikelList[position]

        Glide.with(holder.itemView)
            .load(artikel.photo)
            .into(holder.binding.ivArtikel)

        Glide.with(holder.itemView)
            .load(artikel.imgProfileUser)
            .into(holder.binding.ivWriter)

        holder.binding.tvTitle.text = artikel.title
        holder.binding.tvNameWriter.text = artikel.nameWriter

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Maaf, detail artikel belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

}