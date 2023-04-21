package com.example.projectlathidataapp.Data


import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import com.example.projectlathidataapp.util.Resource

interface AuthRepository {
    fun loginuser(email:String, password:String): Flow<Resource<AuthResult>>
    fun registeruser(email:String, password:String): Flow<Resource<AuthResult>>
}