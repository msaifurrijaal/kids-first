package com.kelompokpam.kidsfirst.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    fun isAuth(): LiveData<Resource<FirebaseUser>> {
        val authResult = MutableLiveData<Resource<FirebaseUser>>()

        val currentUser = firebaseAuth.currentUser
        authResult.value = Resource.Error("")
        currentUser?.let {
            authResult.value = Resource.Success(it)
        }
        return authResult
    }

    fun getDokter(): LiveData<Resource<List<User>>> {
        val dokterLiveData = MutableLiveData<Resource<List<User>>>()
        dokterLiveData.value = Resource.Loading()

        userDatabase.orderByChild("role_user").equalTo("dokter")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<User>()

                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        user?.let {
                            userList.add(it)
                        }
                    }

                    dokterLiveData.value = Resource.Success(userList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    dokterLiveData.value = Resource.Error(databaseError.message)
                }
            })
        return dokterLiveData
    }

    fun getCurrentUser(): LiveData<Resource<User>> {
        val currentUserLiveData = MutableLiveData<Resource<User>>()
        currentUserLiveData.value = Resource.Loading()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            val userReference = userDatabase.child(uid)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        currentUserLiveData.value = Resource.Success(user)
                    } else {
                        currentUserLiveData.value = Resource.Error("User data not found")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    currentUserLiveData.value = Resource.Error(databaseError.message)
                }
            })
        } else {
            currentUserLiveData.value = Resource.Error("User not authenticated")
        }
        return currentUserLiveData
    }

}