package com.kelompokpam.kidsfirst.presentation.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompokpam.kidsfirst.adapter.ListChatAdapter
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.databinding.ActivityListChatBinding
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading

class ListChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ListChatAdapter
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        setChatRv()
        listUserObserve()
        onAction()

    }

    private fun listUserObserve() {
        chatViewModel.getAllUsers().observe(this) { response ->
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
                    chatAdapter.setListUser(response.data!!)
                }
            }
        }
    }

    private fun setChatRv() {
        chatAdapter = ListChatAdapter()
        binding.rvListChat.apply {
            layoutManager = LinearLayoutManager(this@ListChatActivity)
            adapter = chatAdapter
        }
    }

    private fun onAction() {
        binding.apply {
            btnCloseListChat.setOnClickListener {
                finish()
            }

            chatAdapter.onItemClick = { userData ->
                startActivity(Intent(this@ListChatActivity, ChatActivity::class.java)
                    .putExtra(ChatActivity.USER_ITEM, userData)
                )
            }
        }
    }

    companion object {
        const val USER_ITEM = "user_item"
    }
}