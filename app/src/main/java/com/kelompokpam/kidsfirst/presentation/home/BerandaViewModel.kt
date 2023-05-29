package com.kelompokpam.kidsfirst.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class BerandaViewModel(application: Application): AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun getListDokter() = repository.getDokter()
    fun getCurrentUser() = repository.getCurrentUser()
}