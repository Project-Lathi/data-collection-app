package com.example.projectlathidataapp.Screens.Register

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

data class userData(
    val Email:String
)
