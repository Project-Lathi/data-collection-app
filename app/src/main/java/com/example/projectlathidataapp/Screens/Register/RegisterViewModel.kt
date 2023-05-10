package com.example.projectlathidataapp.Screens.Register

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectlathidataapp.Data.AuthRepository
import com.example.projectlathidataapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    val _registerstate = Channel<RegisterState>()
    val registerstate = _registerstate.receiveAsFlow()

    fun registerUser(userName: String, email:String, password:String)=viewModelScope.launch {
        repository.registeruser(userName,email,password).collect{result ->
            when(result){
                is Resource.Success->{
                    _registerstate.send(RegisterState(isSuccess = "Register Successful"))
                    addDataToFirebase(userName)
                }
                is Resource.Loading ->{
                    _registerstate.send(RegisterState(isLoading = true))
                }
                is Resource.Error ->{
                    _registerstate.send(RegisterState(isError = result.message))
                }
            }
        }
    }
    fun addDataToFirebase(
        Email: String
    ) {
        //creating a collection reference for our Firebase Firestore database.
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseFirestore.getInstance().collection("Users").document(uid).set(userData(Email))

    }




}