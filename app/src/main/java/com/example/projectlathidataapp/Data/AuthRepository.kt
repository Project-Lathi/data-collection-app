package com.example.projectlathidataapp.Data


import android.app.Activity
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import com.example.projectlathidataapp.util.Resource

interface AuthRepository {
    fun loginuser(email:String, password:String): Flow<Resource<AuthResult>>
    fun registeruser(userName:String, email:String, password:String): Flow<Resource<AuthResult>>
    fun createUserWithPhone(
        phone:String,
        activity: Activity
    ) : Flow<Resource<String>>

    fun signWithCredential(
        otp:String
    ): Flow<Resource<String>>

}