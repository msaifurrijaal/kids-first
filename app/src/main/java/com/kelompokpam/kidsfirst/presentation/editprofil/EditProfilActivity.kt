package com.kelompokpam.kidsfirst.presentation.editprofil

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
import com.kelompokpam.kidsfirst.databinding.ActivityEditProfilBinding
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading
import com.kelompokpam.kidsfirst.utils.showDialogSuccess

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding
    private lateinit var editProfilViewModel: EditProfilViewModel
    private lateinit var dialogLoading: AlertDialog
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editProfilViewModel = ViewModelProvider(this).get(EditProfilViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        getInformationFromIntent()
        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnCloseEditProfil.setOnClickListener {
                finish()
            }

            btnEditProfil.setOnClickListener {
                val newName = etNamaBaru.text.toString()
                if (user != null) {
                    editProfilViewModel.editProfil(user?.uidUser!!, newName).observe(this@EditProfilActivity) { response ->
                        when(response){
                            is Resource.Error -> {
                                dialogLoading.dismiss()
                                showDialogError(this@EditProfilActivity, response.message.toString())
                            }
                            is Resource.Loading -> {
                                dialogLoading.show()
                            }
                            is Resource.Success -> {
                                val dialogSuccess = showDialogSuccess(
                                    this@EditProfilActivity,
                                    getString(R.string.selamat_profil_anda_berhasil_diubah)
                                )
                                dialogSuccess.show()

                                Handler(Looper.getMainLooper())
                                    .postDelayed({
                                        dialogSuccess.dismiss()
                                        finish()
                                    }, 1500)
                            }
                        }
                    }
                } else {
                    showDialogError(this@EditProfilActivity, getString(R.string.gagal_melakukan_edit_profile_silahkan_kembali_ke_halaman_profil_dan_mencoba_ulang))
                }
            }
        }
    }

    private fun getInformationFromIntent() {
        user = intent.getParcelableExtra<User>(EditProfilActivity.USER_ITEM)
        Log.d("EditProfilActivity", user.toString())
    }

    companion object {
        const val USER_ITEM = "user_item"
    }

}