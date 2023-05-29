package com.kelompokpam.kidsfirst.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.databinding.ActivityDetailDokterBinding
import com.kelompokpam.kidsfirst.databinding.FragmentArtikelBinding
import com.kelompokpam.kidsfirst.databinding.FragmentProfileBinding
import com.kelompokpam.kidsfirst.presentation.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var profilViewModel: ProfilViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profilViewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataUser()
        onAction()
    }

    private fun setDataUser() {
        profilViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Glide.with(this)
                        .load(response.data?.avatarUser)
                        .into(binding.ivUser)

                    binding.tvNameUser.text = response.data?.nameUser
                    binding.tvEmailUser.text = response.data?.emailUser
                }
            }
        })
    }

    private fun onAction() {
        binding.apply {
            btnLogout.setOnClickListener {
                firebaseAuth.signOut()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finishAffinity()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}