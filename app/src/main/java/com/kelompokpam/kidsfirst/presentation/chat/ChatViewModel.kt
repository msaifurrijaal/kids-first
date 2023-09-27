package com.kelompokpam.kidsfirst.presentation.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.ChatRepository
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class ChatViewModel(application: Application): AndroidViewModel(application) {

    val userRepository = UserRepository(application)
    val chatRepository = ChatRepository(application)

    fun getDataUser() = userRepository.getCurrentUser()

    fun getAllUsers() = userRepository.getAllUsers()

    fun sendMessage(receiverId: String, message: String) =
        chatRepository.sendChat(receiverId, message)

    fun readChat(receiverId: String) = chatRepository.readChat(receiverId)

}