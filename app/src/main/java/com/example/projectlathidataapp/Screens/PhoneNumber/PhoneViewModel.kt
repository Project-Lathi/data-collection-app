package com.example.projectlathidataapp.Screens.PhoneNumber

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectlathidataapp.Data.AuthRepository
import com.example.projectlathidataapp.Screens.Register.RegisterState
import com.example.projectlathidataapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){
    val _registerstate = Channel<VerificationState>()
    val registerstate = _registerstate.receiveAsFlow()

    fun createUserWithPhone(phone:String,activity: Activity)=viewModelScope.launch{
        repository.createUserWithPhone(phone, activity).collect{result->
            when(result){
                is Resource.Success->{
                    _registerstate.send(VerificationState(isSuccess = "Verification Successful"))
                }
                is Resource.Loading ->{
                    _registerstate.send(VerificationState(isLoading = true))
                }
                is Resource.Error ->{
                    _registerstate.send(VerificationState(isError = result.message))
                }
            }
        }
    }


    fun signWithCredential(otp:String)=viewModelScope.launch {
        repository.signWithCredential(otp).collect{result->
            when(result){
                is Resource.Success->{
                    _registerstate.send(VerificationState(isSuccess = "Verification Successful"))
                }
                is Resource.Loading ->{
                    _registerstate.send(VerificationState(isLoading = true))
                }
                is Resource.Error ->{
                    _registerstate.send(VerificationState(isError = result.message))
                }
            }
        }
    }
}