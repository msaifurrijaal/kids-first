package com.kelompokpam.kidsfirst.presentation.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.adapter.ChatAdapter
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.ActivityChatBinding
import com.kelompokpam.kidsfirst.utils.showDialogError

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private var dokter: User? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        getInformationFromIntent()
        setInformationChat()
        dataUserObserve()
        setChatRv()
        onAction()
        readChat(dokter!!.uidUser!!)
    }

    private fun dataUserObserve() {
        chatViewModel.getDataUser().observe(this) { response ->
            when(response) {
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    user = response.data
                }
            }
        }
    }

    private fun setChatRv() {
        chatAdapter = ChatAdapter()
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun onAction() {
        binding.apply {
            btnCloseChat.setOnClickListener {
                finish()
            }

            btnIconSend.setOnClickListener {
                var message = etMessage.text.toString()
                if (message.isEmpty()) {
                    Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                    etMessage.setText("")
                } else {
                    sendMessage(dokter!!.uidUser, message, user!!.avatarUser)
                    etMessage.setText("")
                }
            }
        }
    }

    private fun sendMessage(uidUser: String?, message: String, avatarUser: String?) {
        chatViewModel.sendMessage(receiverId = uidUser!!, message = message, imgProfile = avatarUser!!)
            .observe(this) { response ->
                when(response) {
                    is Resource.Error -> {
                        showDialogError(this, response.message.toString())
                    }
                    is Resource.Loading -> { }
                    is Resource.Success -> {
                        readChat(uidUser)
                    }
                }
            }
    }

    private fun readChat(receiverId: String) {
        chatViewModel.readChat(receiverId).observe(this) { response ->
            when(response) {
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    chatAdapter.setChatList(response.data!!)
                }
            }
        }
    }

    private fun setInformationChat() {
        dokter?.let {
            Glide.with(this)
                .load(dokter!!.avatarUser)
                .placeholder(R.color.gray)
                .into(binding.ivUser)

            binding.tvUsername.text = dokter!!.nameUser
        }
    }

    private fun getInformationFromIntent() {
        dokter = intent.getParcelableExtra<User>(ChatActivity.USER_ITEM)
        Log.d("ChatActivity", dokter.toString())
    }

    companion object {
        const val USER_ITEM = "user_item"
    }
}