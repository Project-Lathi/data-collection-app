package com.example.projectlathidataapp.Screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectlathidataapp.Data.AuthRepository
import com.example.projectlathidataapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
):ViewModel (){

    val _loginstate = Channel<RegisterState>()
    val loginstate = _loginstate.receiveAsFlow()

    fun loginUser(email:String, password:String)=viewModelScope.launch {
        repository.loginuser(email,password).collect{result ->
            when(result){
                is Resource.Success->{
                    _loginstate.send(RegisterState(isSuccess = "Login Successful"))
                }
                is Resource.Loading ->{
                    _loginstate.send(RegisterState(isLoading = true))
                }
                is Resource.Error ->{
                    _loginstate.send(RegisterState(isError = result.message))
                }
            }
        }
    }
}