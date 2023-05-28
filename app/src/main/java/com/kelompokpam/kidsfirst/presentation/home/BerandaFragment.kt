package com.kelompokpam.kidsfirst.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.kelompokpam.kidsfirst.databinding.FragmentArtikelBinding
import com.kelompokpam.kidsfirst.databinding.FragmentBerandaBinding
import com.kelompokpam.kidsfirst.presentation.detaildokter.DetailDokterActivity

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var berandaViewModel: BerandaViewModel
    private lateinit var dokterAdapter : DokterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        berandaViewModel = ViewModelProvider(this).get(BerandaViewModel::class.java)
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)

        dokterAdapter = DokterAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserData()
        observeListDokter()
        onItemDokterClick()

    }

    private fun observeUserData() {
        berandaViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.tvHaiUser.text = "Hai, ${response.data?.nameUser}"
                }
            }
        })
    }

    private fun onItemDokterClick() {
        dokterAdapter.onItemClick = { dokter ->
            startActivity(Intent(activity, DetailDokterActivity::class.java)
                .putExtra(DetailDokterActivity.USER_ITEM, dokter))
        }
    }

    private fun observeListDokter() {
        berandaViewModel.getListDokter().observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Error -> {
                    binding.rvDokter.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "error : ${response.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pgRvDokter.visibility = View.VISIBLE
                    binding.rvDokter.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    binding.pgRvDokter.visibility = View.INVISIBLE
                    setDokterRv(response.data)
                    binding.rvDokter.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setDokterRv(data: List<User>?) {
        data?.let {
            dokterAdapter.setListDokter(data.take(2))
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