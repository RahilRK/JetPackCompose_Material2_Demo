package com.example.jetpackcompose_material2_demo.ui.home.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun FloatingActionButtonC(onClick: () -> Unit = {}) {
    FloatingActionButton(onClick = { onClick() }, backgroundColor = Color.Black) {

        Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White)
    }
}