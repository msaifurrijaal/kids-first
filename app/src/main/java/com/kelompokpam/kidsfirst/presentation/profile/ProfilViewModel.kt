package com.kelompokpam.kidsfirst.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class ProfilViewModel(application: Application): AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun getCurrentUser() = repository.getCurrentUser()
}