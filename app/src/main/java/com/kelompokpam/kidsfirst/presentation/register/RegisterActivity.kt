package com.kelompokpam.kidsfirst.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompokpam.kidsfirst.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()

    }

    private fun onAction() {
        binding.apply {
            toolbarInclude.btnCloseRegister.setOnClickListener {
                finish()
            }
        }
    }
}