package com.kelompokpam.kidsfirst.presentation.lupapassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class LupaPasswordViewModel(application: Application): AndroidViewModel(application) {

    val repository = UserRepository(application)

    fun resetPass(emailUser: String) = repository.resetPassword(emailUser)

}