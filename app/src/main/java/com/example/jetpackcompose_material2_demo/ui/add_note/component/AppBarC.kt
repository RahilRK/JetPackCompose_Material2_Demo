package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppBarC(
    title: String = "Add Note",
    showBackArrow: Boolean = false,
    showSubmitIcon: Boolean = false,
    onSubmitClick: () -> Unit = {},
    onBackPress: () -> Unit = {},
) {
    TopAppBar(title = { Text(text = title) }, navigationIcon = {

        if(showBackArrow) {
            IconButton(onClick = { onBackPress() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            }

        }
        else {

        }
    }, actions = {
        if(showSubmitIcon) {
            IconButton(onClick = { onSubmitClick() }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
    }, backgroundColor = Color.White)
}