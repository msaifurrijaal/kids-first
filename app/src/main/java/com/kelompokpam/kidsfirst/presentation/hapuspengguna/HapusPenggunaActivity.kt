package com.kelompokpam.kidsfirst.presentation.hapuspengguna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.ActivityHapusPenggunaBinding
import com.kelompokpam.kidsfirst.presentation.login.LoginActivity
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading
import com.kelompokpam.kidsfirst.utils.showDialogSuccess

class HapusPenggunaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHapusPenggunaBinding
    private lateinit var dialogLoading: AlertDialog
    private lateinit var hapusPenggunaViewModel: HapusPenggunaViewModel
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHapusPenggunaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hapusPenggunaViewModel = ViewModelProvider(this).get(HapusPenggunaViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        getInformationFromIntent()
        onAction()

    }

    private fun onAction() {
        binding.apply {
            btnClose.setOnClickListener {
                finish()
            }

            btnHapus.setOnClickListener {
                if (user != null) {
                    val email = user?.emailUser
                    val password = etKataSandi.text.toString()

                    hapusPenggunaViewModel.deleteAuth(email!!, password).observe(this@HapusPenggunaActivity){ response ->
                        when(response){
                            is Resource.Error -> {
                                dialogLoading.dismiss()
                                showDialogError(this@HapusPenggunaActivity, response.message.toString())
                            }
                            is Resource.Loading -> {
                                dialogLoading.show()
                            }
                            is Resource.Success -> {
                                dialogLoading.dismiss()
                                deleteUserOnDatabase()
                            }
                        }
                    }
                } else {
                    showDialogError(this@HapusPenggunaActivity, getString(R.string.gagal_melakukan_edit_profile_silahkan_kembali_ke_halaman_profil_dan_mencoba_ulang))
                }

            }
        }
    }

    private fun deleteUserOnDatabase() {
        hapusPenggunaViewModel.deleteUser(user?.uidUser!!).observe(this@HapusPenggunaActivity) { response ->
            when (response) {
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this, response.message.toString())
                }

                is Resource.Loading -> {
                    dialogLoading.show()
                }

                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val dialogSuccess = showDialogSuccess(
                        this,
                        "Selamat, akun anda berhasil dihapus"
                    )
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            dialogSuccess.dismiss()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finishAffinity()
                        }, 3000)
                }
            }
        }
    }

    private fun getInformationFromIntent() {
        user = intent.getParcelableExtra<User>(HapusPenggunaActivity.USER_ITEM)
    }

    companion object {
        const val USER_ITEM = "user_item"
    }
}