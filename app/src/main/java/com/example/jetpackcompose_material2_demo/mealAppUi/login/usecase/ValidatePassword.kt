package com.example.jetpackcompose_material2_demo.mealAppUi.login.usecase

import com.example.jetpackcompose_material2_demo.util.ValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter Password"
            )
        }

/*
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Email Id"
            )
        }
*/

        return ValidationResult(
            successful = true
        )
    }
}