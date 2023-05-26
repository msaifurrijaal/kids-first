package com.kelompokpam.kidsfirst.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class LoginViewModel(application: Application): AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun userAuth(email: String, password: String) = repository.userAuth(email, password)
    fun isAuth() = repository.isAuth()
}