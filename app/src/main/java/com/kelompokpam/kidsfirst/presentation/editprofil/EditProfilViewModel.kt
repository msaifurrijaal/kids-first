package com.kelompokpam.kidsfirst.presentation.editprofil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class EditProfilViewModel(application: Application): AndroidViewModel(application) {

    val repository = UserRepository(application)

    fun editProfil(userId: String, newName: String) = repository.editProfil(userId, newName)
}