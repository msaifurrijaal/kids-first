package com.kelompokpam.kidsfirst.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.presentation.main.MainActivity
import com.kelompokpam.kidsfirst.databinding.ActivityLoginBinding
import com.kelompokpam.kidsfirst.presentation.register.RegisterActivity
import com.kelompokpam.kidsfirst.utils.hideSoftKeyboard
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            etContainerRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            btnMasuk.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val pass = etPassword.text.toString().trim()

                if (checkValidation(email, pass)) {
                    hideSoftKeyboard(this@LoginActivity, binding.root)
                    loginToServer(email, pass)
                }
            }
        }
    }

    private fun loginToServer(email: String, pass: String) {
        loginViewModel.userAuth(email, pass).observe(this){ response ->
            when(response) {
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this@LoginActivity, response.message.toString())
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }

    private fun checkValidation(email: String, pass: String): Boolean {
        binding.apply {
            when{
                email.isEmpty() -> {
                    etEmail.error = getString(R.string.silahkan_isi_email_anda)
                    etEmail.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = getString(R.string.silahkan_gunakan_email_yang_valid)
                    etEmail.requestFocus()
                }
                pass.isEmpty() -> {
                    etPassword.error = getString(R.string.silahkan_isi_kata_sandi_anda)
                    etPassword.requestFocus()
                }
                pass.length < 8 -> {
                    etPassword.error = getString(R.string.silahkan_isi_kata_sandi_minimal_8_karakter)
                    etPassword.requestFocus()
                }
                else -> return true
            }
        }
        return false
    }
}