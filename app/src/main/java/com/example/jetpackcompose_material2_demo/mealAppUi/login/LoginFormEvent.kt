package com.example.jetpackcompose_material2_demo.mealAppUi.login

sealed class LoginFormEvent{
    data class EmailChange(val email: String): LoginFormEvent()
    data class PasswordChange(val password: String): LoginFormEvent()

    object Submit: LoginFormEvent()
}