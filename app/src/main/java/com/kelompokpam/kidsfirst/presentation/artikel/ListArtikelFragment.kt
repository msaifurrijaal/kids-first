package com.kelompokpam.kidsfirst.presentation.artikel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.adapter.ArtikelAdapter
import com.kelompokpam.kidsfirst.data.dummy.ArtikelsData
import com.kelompokpam.kidsfirst.data.model.Artikel
import com.kelompokpam.kidsfirst.databinding.FragmentBerandaBinding
import com.kelompokpam.kidsfirst.databinding.FragmentListArtikelBinding


class ListArtikelFragment : Fragment() {

    private var _binding: FragmentListArtikelBinding? = null
    private val binding get() = _binding!!
    private lateinit var artikelAdapter: ArtikelAdapter
    private var list: ArrayList<Artikel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListArtikelBinding.inflate(inflater, container, false)

        artikelAdapter = ArtikelAdapter()

        list.addAll(ArtikelsData.listData)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArtikelRV()
    }

    private fun setArtikelRV() {
        artikelAdapter.setArtikelList(list)

        binding.rvArtikel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = artikelAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}