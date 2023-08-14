package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BGColorC() {
    val Green = Color(0xFF00eea4)
    val Blue = Color(0xFF4455fb)
    val Red = Color(0xFFff225c)
    val Yellow = Color(0xFFffaf47)

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = CircleShape)
                .background(Green)
                .border(width = 2.dp, color = Color.Black, shape = CircleShape)
        ) {

        }
    }
}