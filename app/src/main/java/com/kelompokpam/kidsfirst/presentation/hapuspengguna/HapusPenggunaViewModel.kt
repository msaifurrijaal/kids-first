package com.kelompokpam.kidsfirst.presentation.hapuspengguna

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class HapusPenggunaViewModel(application: Application): AndroidViewModel(application) {

    val repository = UserRepository(application)

    fun deleteAuth(emailUser: String, password: String) = repository.deleteAuth(emailUser, password)
    fun deleteUser(userId: String) = repository.deleteUser(userId)
}