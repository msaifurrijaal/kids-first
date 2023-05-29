package com.kelompokpam.kidsfirst.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.databinding.ActivityRegisterBinding
import com.kelompokpam.kidsfirst.utils.hideSoftKeyboard
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading
import com.kelompokpam.kidsfirst.utils.showDialogSuccess

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        onAction()

    }

    private fun onAction() {
        binding.apply {

            tvContainerMasuk.setOnClickListener {
                finish()
            }

            btnDaftar.setOnClickListener {
                val name = etNamaLengkap.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val pass = etKataSandi.text.toString().trim()
                val confirmPass = etKonfirKataSandi.text.toString().trim()

                if (checkValidation(name, email, pass, confirmPass)) {
                    hideSoftKeyboard(this@RegisterActivity, binding.root)
                    createUserAuth(name, email, pass)
                }
            }

            btnCloseRegister.setOnClickListener {
                finish()
            }


        }
    }

    private fun createUserAuth(name:String, email: String, pass: String) {
        registerViewModel.createAuthUser(email, pass).observe(this@RegisterActivity) { response ->
            when(response) {
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this@RegisterActivity, response.message.toString())
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    createUserOnDatabase(response.data?.uid, name, email)
                }
            }
        }
    }

    private fun createUserOnDatabase(uid: String?, name: String, email: String) {
        registerViewModel.createUser(uid!!, name, email).observe(this@RegisterActivity) { response ->
            when(response){
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this@RegisterActivity, response.message.toString())
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val dialogSuccess = showDialogSuccess(
                        this,
                        getString(R.string.selamat_akun_anda_berhasil_dibuat)
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
    }

    private fun checkValidation(
        name: String,
        email: String,
        pass: String,
        confirmPass: String
    ): Boolean {
        binding.apply {
            when{
                name.isEmpty() -> {
                    etNamaLengkap.error = getString(R.string.silahkan_isi_nama_anda)
                    etNamaLengkap.requestFocus()
                }
                email.isEmpty() -> {
                    etEmail.error = getString(R.string.silahkan_isi_email_anda)
                    etEmail.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = getString(R.string.silahkan_gunakan_email_yang_valid)
                    etEmail.requestFocus()
                }
                pass.isEmpty() -> {
                    etKataSandi.error = getString(R.string.silahkan_isi_kata_sandi_anda)
                    etKataSandi.requestFocus()
                }
                pass.length < 8 -> {
                    etKataSandi.error = getString(R.string.silahkan_isi_kata_sandi_minimal_8_karakter)
                    etKataSandi.requestFocus()
                }
                confirmPass.isEmpty() -> {
                    etKonfirKataSandi.error = getString(R.string.silahkan_isi_konfirmasi_kata_sandi)
                    etKonfirKataSandi.requestFocus()
                }
                confirmPass.length < 8 -> {
                    etKonfirKataSandi.error = getString(R.string.silahkan_isi_konfirmasi_kata_sandi_minimal_8_karakter)
                    etKonfirKataSandi.requestFocus()
                }
                pass != confirmPass -> {
                    etKataSandi.error = getString(R.string.kata_sandi_dan_konfirmasi_tidak_sama)
                    etKataSandi.requestFocus()
                    etKonfirKataSandi.error = getString(R.string.kata_sandi_dan_konfirmasi_tidak_sama)
                    etKonfirKataSandi.requestFocus()
                }
                else -> return true
            }
        }
        return false
    }

}