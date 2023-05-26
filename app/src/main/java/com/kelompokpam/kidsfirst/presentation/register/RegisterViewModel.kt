package com.kelompokpam.kidsfirst.presentation.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class RegisterViewModel(application: Application): AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun createAuthUser(email: String, password: String) = repository.createAuth(email, password)
    fun createUser(uid: String, name:String, email: String) = repository.createUser(uid, name, email)

}