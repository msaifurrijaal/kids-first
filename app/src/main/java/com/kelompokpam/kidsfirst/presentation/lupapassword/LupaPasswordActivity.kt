package com.kelompokpam.kidsfirst.presentation.lupapassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.databinding.ActivityLupaPasswordBinding
import com.kelompokpam.kidsfirst.presentation.login.LoginActivity
import com.kelompokpam.kidsfirst.presentation.main.MainActivity
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading

class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaPasswordBinding
    private lateinit var lupaPasswordViewModel: LupaPasswordViewModel
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lupaPasswordViewModel = ViewModelProvider(this).get(LupaPasswordViewModel::class.java)
        dialogLoading = showDialogLoading(this)
        onAction()

    }

    private fun onAction() {
        binding.apply {
            btnCloseLupaPassword.setOnClickListener {
                finish()
            }

            btnEmail.setOnClickListener {
                lupaPasswordObserve()
            }
        }

    }

    private fun lupaPasswordObserve() {
        val emailUser = binding.etEmail.text.toString()
        if (checkValidation(emailUser)){
            lupaPasswordViewModel.resetPass(emailUser).observe(this) { response ->
                when(response) {
                    is Resource.Error -> {
                        dialogLoading.dismiss()
                        showDialogError(this, response.message.toString())
                    }
                    is Resource.Loading -> {
                        dialogLoading.show()
                    }
                    is Resource.Success -> {
                        dialogLoading.dismiss()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }

    }

    private fun checkValidation(email: String): Boolean {
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
                else -> return true
            }
        }
        return false
    }
}