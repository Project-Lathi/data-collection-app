package com.example.projectlathidataapp.Screens.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectlathidataapp.Data.AuthRepository
import com.example.projectlathidataapp.Screens.Login.RegisterState
import com.example.projectlathidataapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    val _registerstate = Channel<RegisterState>()
    val registerstate = _registerstate.receiveAsFlow()

    fun registerUser(email:String, password:String)=viewModelScope.launch {
        repository.registeruser(email,password).collect{result ->
            when(result){
                is Resource.Success->{
                    _registerstate.send(RegisterState(isSuccess = "Register Successful"))
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
}