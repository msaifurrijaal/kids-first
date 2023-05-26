package com.kelompokpam.kidsfirst.presentation.detaildokter

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.databinding.ActivityDetailDokterBinding

class DetailDokterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDokterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            tvHargaAsal.paintFlags = tvHargaAsal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }
}