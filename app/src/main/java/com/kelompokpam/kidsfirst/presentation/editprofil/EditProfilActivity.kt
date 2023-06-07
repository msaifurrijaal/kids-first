package com.kelompokpam.kidsfirst.presentation.editprofil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.data.model.User
import com.kelompokpam.kidsfirst.databinding.ActivityEditProfilBinding
import com.kelompokpam.kidsfirst.utils.createCustomTempFile
import com.kelompokpam.kidsfirst.utils.showDialogError
import com.kelompokpam.kidsfirst.utils.showDialogLoading
import com.kelompokpam.kidsfirst.utils.showDialogSuccess
import java.io.File

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding
    private lateinit var editProfilViewModel: EditProfilViewModel
    private lateinit var dialogLoading: AlertDialog
    private var user: User? = null
    private var imageUser: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editProfilViewModel = ViewModelProvider(this).get(EditProfilViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        getInformationFromIntent()
        setInformationFromIntent()
        onAction()
    }

    private fun onAction() {
        binding.apply {

            ivUser.setOnClickListener {
                if (!allPermissionsGranted()) {
                    requestCameraPermission()
                } else if (allPermissionsGranted()) {
                    startTakePhoto()
                }
            }

            btnCloseEditProfil.setOnClickListener {
                finish()
            }

            btnEditProfil.setOnClickListener {
                val newName = etNamaBaru.text.toString()
                if (user != null && imageUser != null && !newName.isEmpty()) {
                    uploadImageToServe(newName)
                } else if (user != null && imageUser == null && !newName.isEmpty()) {
                    editUsername(newName)
                } else {
                    showDialogError(this@EditProfilActivity, getString(R.string.gagal_melakukan_edit_profile_silahkan_kembali_ke_halaman_profil_dan_mencoba_ulang))
                }
            }
        }
    }

    private fun uploadImageToServe(newName: String) {
        editProfilViewModel.uploadImage(imageUser!!).observe(this) { response ->
            when (response) {
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this@EditProfilActivity, response.message.toString())
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val imageUrl = response.data
                    editPhotoAndName(newName, imageUrl)
                }
            }
        }
    }

    private fun editPhotoAndName(newName: String, imageUrl: String?) {
        editProfilViewModel.editPhotoAndUsername(newName, imageUrl!!).observe(this) { response ->
            when(response) {
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this@EditProfilActivity, response.message.toString())
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val dialogSuccess = showDialogSuccess(
                        this@EditProfilActivity,
                        getString(R.string.selamat_profil_anda_berhasil_diubah)
                    )
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            dialogSuccess.dismiss()
                            finish()
                        }, 1500)
                }
            }
        }
    }

    private fun editUsername(newName: String) {
        editProfilViewModel.editProfil(user?.uidUser!!, newName).observe(this@EditProfilActivity) { response ->
            when(response){
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    showDialogError(this@EditProfilActivity, response.message.toString())
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val dialogSuccess = showDialogSuccess(
                        this@EditProfilActivity,
                        getString(R.string.selamat_nama_anda_berhasil_di_update)
                    )
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            dialogSuccess.dismiss()
                            finish()
                        }, 1500)
                }
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@EditProfilActivity,
                "com.kelompokpam.kidsfirst",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            imageUser =  BitmapFactory.decodeFile(myFile.path)

            binding.ivUser.setImageBitmap(imageUser)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (allPermissionsGranted()) {
                startTakePhoto()
            }
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setInformationFromIntent() {
        binding.apply {
            Glide.with(this@EditProfilActivity)
                .load(user?.avatarUser)
                .into(ivUser)

            etNamaBaru.setText(user?.nameUser)
        }
    }

    private fun getInformationFromIntent() {
        user = intent.getParcelableExtra<User>(EditProfilActivity.USER_ITEM)
    }

    companion object {
        const val USER_ITEM = "user_item"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}