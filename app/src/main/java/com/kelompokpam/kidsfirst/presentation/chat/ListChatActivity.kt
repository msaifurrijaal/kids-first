package com.kelompokpam.kidsfirst.presentation.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompokpam.kidsfirst.databinding.ActivityListChatBinding

class ListChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()

    }

    private fun onAction() {
        binding.apply {
            btnCloseListChat.setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        const val USER_ITEM = "user_item"
    }
}