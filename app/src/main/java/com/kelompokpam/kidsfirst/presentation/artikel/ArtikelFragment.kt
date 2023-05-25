package com.kelompokpam.kidsfirst.presentation.artikel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelompokpam.kidsfirst.adapter.TabsPagerAdapter
import com.kelompokpam.kidsfirst.databinding.FragmentArtikelBinding

class ArtikelFragment : Fragment() {

    private var _binding: FragmentArtikelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtikelBinding.inflate(inflater, container, false)

        val adapter = TabsPagerAdapter( requireContext(), childFragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}