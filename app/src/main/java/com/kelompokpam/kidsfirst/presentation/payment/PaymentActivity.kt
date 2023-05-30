package com.kelompokpam.kidsfirst.presentation.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.ActivityPaymentBinding
import com.kelompokpam.kidsfirst.presentation.detaildokter.DetailDokterActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private var dokter: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getInformationFromIntent()
        setInformationPayment()
        onAction()

    }

    private fun onAction() {
        binding.apply {
            btnClosePayment.setOnClickListener {
                finish()
            }
        }
    }

    private fun setInformationPayment() {
        dokter?.let {
            Glide.with(this)
                .load(dokter!!.avatarUser)
                .into(binding.ivDokter)

            binding.tvNamaDokter.text = dokter!!.nameUser
        }
    }

    private fun getInformationFromIntent() {
        dokter = intent.getParcelableExtra<User>(DetailDokterActivity.USER_ITEM)
    }

    companion object {
        const val USER_ITEM = "user_item"
    }

}