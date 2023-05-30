package com.kelompokpam.kidsfirst.presentation.detaildokter

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.ActivityDetailDokterBinding
import com.kelompokpam.kidsfirst.presentation.payment.PaymentActivity

class DetailDokterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDokterBinding
    private var dokter: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getInformationFromIntent()
        setProfileDokter()

        onAction()
    }

    private fun setProfileDokter() {
        dokter?.let {
            Glide.with(this)
                .load(dokter!!.avatarUser)
                .into(binding.imgDokterDetail)

            binding.tvNamaDokter.text = dokter!!.nameUser
        }
    }

    private fun getInformationFromIntent() {
        dokter = intent.getParcelableExtra<User>(USER_ITEM)
    }

    private fun onAction() {
        binding.apply {
            tvHargaAsal.paintFlags = tvHargaAsal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            btnChat.setOnClickListener {
                startActivity(Intent(this@DetailDokterActivity, PaymentActivity::class.java)
                    .putExtra(PaymentActivity.USER_ITEM, dokter)
                )
            }
        }
    }

    companion object {
        const val USER_ITEM = "user_item"
    }
}