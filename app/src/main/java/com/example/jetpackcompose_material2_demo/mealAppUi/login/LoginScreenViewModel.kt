package com.example.jetpackcompose_material2_demo.mealAppUi.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.mealAppUi.login.usecase.ValidateEmail
import com.example.jetpackcompose_material2_demo.mealAppUi.login.usecase.ValidatePassword
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: MainRepository,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,

) : ViewModel() {

    private val TAG = "LoginScreenViewModel"

    var state by mutableStateOf(LoginFormState())

    private val _validationEvent = MutableStateFlow<ValidationEvent>(ValidationEvent.Error)
    val validationEvent = _validationEvent.asStateFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChange -> {
                state = state.copy(email = event.email, emailError = "")
            }

            is LoginFormEvent.PasswordChange -> {
                state = state.copy(password = event.password, passwordError = "")
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any{
            !it.successful
        }

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )

            return
        }

        viewModelScope.launch {
            _validationEvent.emit(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
        object Error: ValidationEvent()
    }
}