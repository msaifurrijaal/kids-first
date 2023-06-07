package com.kelompokpam.kidsfirst.presentation.editprofil

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class EditProfilViewModel(application: Application): AndroidViewModel(application) {

    val repository = UserRepository(application)

    fun editProfil(userId: String, newName: String) = repository.updateName(userId, newName)

    fun uploadImage(bitmap: Bitmap) = repository.uploadImage(bitmap)

    fun editPhotoAndUsername(newName: String, newAvatar: String) = repository.updatePhotoAndName(newName, newAvatar)
}