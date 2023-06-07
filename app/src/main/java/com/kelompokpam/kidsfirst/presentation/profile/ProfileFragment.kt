package com.kelompokpam.kidsfirst.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.FragmentProfileBinding
import com.kelompokpam.kidsfirst.presentation.chat.ListChatActivity
import com.kelompokpam.kidsfirst.presentation.editprofil.EditProfilActivity
import com.kelompokpam.kidsfirst.presentation.hapuspengguna.HapusPenggunaActivity
import com.kelompokpam.kidsfirst.presentation.login.LoginActivity
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading
import com.kelompokpam.kidsfirst.utils.showDialogSuccess

class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var profilViewModel: ProfilViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var dialogLoading: AlertDialog
    private val binding get() = _binding!!
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        dialogLoading = showDialogLoading(requireContext())
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

    override fun onResume() {
        super.onResume()

        setDataUser()
    }

    private fun setDataUser() {
        profilViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Error -> { }
                is Resource.Loading -> {
                    loadingResponse()
                }
                is Resource.Success -> {
                    Glide.with(this)
                        .load(response.data?.avatarUser)
                        .into(binding.ivUser)

                    binding.tvNameUser.text = response.data?.nameUser
                    binding.tvEmailUser.text = response.data?.emailUser

                    successResponse()

                    user = response.data
                }
            }
        })
    }

    private fun successResponse() {
        binding.apply {
            pgProfile.visibility = View.INVISIBLE
            ivUser.visibility = View.VISIBLE
            tvNameUser.visibility = View.VISIBLE
            tvEmailUser.visibility = View.VISIBLE
        }
    }

    private fun loadingResponse() {
        binding.apply {
            pgProfile.visibility = View.VISIBLE
            ivUser.visibility = View.INVISIBLE
            tvNameUser.visibility = View.INVISIBLE
            tvEmailUser.visibility = View.INVISIBLE
        }
    }

    private fun onAction() {
        binding.apply {
            btnLogout.setOnClickListener {
                firebaseAuth.signOut()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finishAffinity()
            }

            btnEditProfile.setOnClickListener {
                user?.let {
                    startActivity(Intent(requireContext(), EditProfilActivity::class.java)
                        .putExtra(EditProfilActivity.USER_ITEM, user)
                    )
                }
            }

            btnChat.setOnClickListener {
                startActivity(Intent(requireContext(), ListChatActivity::class.java)
                    .putExtra(ListChatActivity.USER_ITEM, user)
                )
            }

            btnHapusAkun.setOnClickListener {
                user?.let {
                    startActivity(Intent(requireContext(), HapusPenggunaActivity::class.java)
                        .putExtra(HapusPenggunaActivity.USER_ITEM, user)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}