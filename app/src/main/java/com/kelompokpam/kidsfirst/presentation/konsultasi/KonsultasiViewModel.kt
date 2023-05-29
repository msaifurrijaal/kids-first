package com.kelompokpam.kidsfirst.presentation.konsultasi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kelompokpam.kidsfirst.data.repository.UserRepository

class KonsultasiViewModel(application: Application): AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun getListDokter() = repository.getDokter()
}