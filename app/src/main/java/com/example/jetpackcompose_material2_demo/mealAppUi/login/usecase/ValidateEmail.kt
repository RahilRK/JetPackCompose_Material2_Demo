package com.example.jetpackcompose_material2_demo.mealAppUi.login.usecase

import com.example.jetpackcompose_material2_demo.util.ValidationResult
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter Email Id"
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