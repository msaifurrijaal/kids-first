package com.kelompokpam.kidsfirst.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.LayoutItemDokterBinding

class DokterAdapter: RecyclerView.Adapter<DokterAdapter.ViewHolder>() {

    lateinit var onItemClick: ((User) -> Unit)

    class ViewHolder(var binding: LayoutItemDokterBinding): RecyclerView.ViewHolder(binding.root)

    private var listDokter = ArrayList<User>()

    fun setListDokter(newListDokter: List<User>) {
        listDokter.clear()
        listDokter.addAll(newListDokter)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutItemDokterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = listDokter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dokter = listDokter[position]
        Glide.with(holder.itemView)
            .load(dokter.avatarUser)
            .into(holder.binding.ivDokter)

        holder.binding.tvNamaDokter.text = dokter.nameUser

        holder.itemView.setOnClickListener {
            onItemClick.invoke(dokter)
        }
    }
}