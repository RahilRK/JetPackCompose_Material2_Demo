package com.example.jetpackcompose_material2_demo.mealAppUi.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose_material2_demo.R
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary

@Preview
@Composable
fun LoginScreen() {
    val viewModel: LoginScreenViewModel = viewModel()
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            if (event == LoginScreenViewModel.ValidationEvent.Success) {
                Toast.makeText(context, "Login successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "saspik",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Column(Modifier.padding(16.dp)) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = painterResource(id = R.drawable.diet),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    text = "Login to your account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = state.email,
                    singleLine = true,
                    onValueChange = { input ->
                        viewModel.onEvent(LoginFormEvent.EmailChange(input))
                    },
                    placeholder = {
                        Text(text = "Email", fontWeight = FontWeight.SemiBold)
                    },
                    isError = state.emailError != "",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 8.dp)
                )
                if (state.emailError != "") {
                    Text(
                        text = state.emailError,
                        color = MaterialTheme.colors.error,
                    )
                }

                OutlinedTextField(
                    value = state.password,
                    singleLine = true,
                    onValueChange = { input ->
                        viewModel.onEvent(LoginFormEvent.PasswordChange(input))
                    },
                    placeholder = {
                        Text(text = "Password", fontWeight = FontWeight.SemiBold)
                    },
                    isError = state.passwordError != "",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.onEvent(LoginFormEvent.Submit)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 8.dp)
                )
                if (state.passwordError != "") {
                    Text(
                        text = state.passwordError,
                        color = MaterialTheme.colors.error,
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    text = "Or Continue With",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { /*TODO*/ },
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 8.dp,
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(6.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = R.drawable.facebook),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Facebook",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { /*TODO*/ },
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 8.dp,
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(6.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Google",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    text = "Forgot Your Password?",
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        viewModel.onEvent(LoginFormEvent.Submit)
                    },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                    )
                ) {
                    Row {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = "Login",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}