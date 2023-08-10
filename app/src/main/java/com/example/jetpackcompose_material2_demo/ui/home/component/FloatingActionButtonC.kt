package com.example.jetpackcompose_material2_demo.ui.home.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloatingActionButtonC(onClick: () -> Unit) {
    FloatingActionButton(onClick = { onClick() }, backgroundColor = MaterialTheme.colors.primary) {

        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
    }
}