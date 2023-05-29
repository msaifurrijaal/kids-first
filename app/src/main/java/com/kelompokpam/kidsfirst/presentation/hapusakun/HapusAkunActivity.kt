package com.kelompokpam.kidsfirst.presentation.hapusakun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle

import com.kelompokpam.kidsfirst.R;
import com.kelompokpam.kidsfirst.databinding.ActivityHapusAkunBinding

class HapusAkunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHapusAkunBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityHapusAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}