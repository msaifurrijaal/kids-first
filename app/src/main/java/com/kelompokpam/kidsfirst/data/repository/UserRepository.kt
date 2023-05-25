package com.kelompokpam.kidsfirst.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kelompokpam.kidsfirst.data.Resource
import com.kelompokpam.kidsfirst.data.model.User

class UserRepository(application: Application) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")

    fun createAuth(email: String, password: String): LiveData<Resource<FirebaseUser>> {
        val authResult = MutableLiveData<Resource<FirebaseUser>>()

        authResult.value = Resource.Loading()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val user = it.result.user
                authResult.value = Resource.Success(user)
            }
            .addOnFailureListener {
                val message = it.message.toString()
                authResult.value = Resource.Error(message)
            }
        return authResult
    }

    fun createUser(uid:String, name: String, email: String): LiveData<Resource<Boolean>> {
        val userResult = MutableLiveData<Resource<Boolean>>()
        userResult.postValue(Resource.Loading())
        userDatabase.child(uid.toString())
            .setValue(
                User(
                    uidUser = uid,
                    roleUser = "dokter",
                    nameUser = name,
                    emailUser = email,
                    avatarUser = "https://ui-avatars.com/api/?background=218B5E&color=fff&size=100&rounded=true&name=$name"
                )
            )
            .addOnSuccessListener {
                userResult.postValue(Resource.Success(true))
            }
            .addOnFailureListener {
                val message = it.message
                userResult.postValue(Resource.Error(message))
            }

        return userResult
    }

    fun userAuth(email: String, password: String): LiveData<Resource<FirebaseUser>> {
        val authResult = MutableLiveData<Resource<FirebaseUser>>()

        authResult.value = Resource.Loading()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = it.user
                authResult.value = Resource.Success(user)
            }
            .addOnFailureListener {
                val message = it.message.toString()
                authResult.value = Resource.Error(message)
            }
        return authResult
    }

}