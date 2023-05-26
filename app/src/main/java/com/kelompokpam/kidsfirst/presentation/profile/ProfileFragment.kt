package com.kelompokpam.kidsfirst.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.databinding.ActivityDetailDokterBinding
import com.kelompokpam.kidsfirst.databinding.FragmentArtikelBinding
import com.kelompokpam.kidsfirst.databinding.FragmentProfileBinding
import com.kelompokpam.kidsfirst.presentation.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onAction()
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