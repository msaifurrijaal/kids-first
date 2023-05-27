package com.kelompokpam.kidsfirst.presentation.konsultasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.adapter.DokterAdapter
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.FragmentBerandaBinding
import com.kelompokpam.kidsfirst.databinding.FragmentKonsultasiBinding
import com.kelompokpam.kidsfirst.presentation.home.BerandaViewModel

class KonsultasiFragment : Fragment() {

    private var _binding: FragmentKonsultasiBinding? = null
    private val binding get() = _binding!!
    private lateinit var konsultasiViewModel: KonsultasiViewModel
    private lateinit var dokterAdapter : DokterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        konsultasiViewModel = ViewModelProvider(this).get(KonsultasiViewModel::class.java)
        _binding = FragmentKonsultasiBinding.inflate(inflater, container, false)

        dokterAdapter = DokterAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        konsultasiViewModel.getListDokter().observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Error -> {
                    binding.rvDokter.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "error : ${response.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pgKonsultasi.visibility = View.VISIBLE
                    binding.rvDokter.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    binding.pgKonsultasi.visibility = View.INVISIBLE
                    setDokterRv(response.data)
                    binding.rvDokter.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setDokterRv(data: List<User>?) {
        data?.let {
            dokterAdapter.setListDokter(data)
        }
        binding.rvDokter.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = dokterAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}