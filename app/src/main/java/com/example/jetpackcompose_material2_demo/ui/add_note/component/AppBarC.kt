package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AppBarC(
    title: String = "Add Note",
    showBackArrow: Boolean = false,
    showDeleteNoteIcon: Boolean = false,
    showSubmitIcon: Boolean = false,
    onDeleteClick: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onBackPress: () -> Unit = {},
) {
    TopAppBar(title = { Text(text = title) }, navigationIcon = {

        if (showBackArrow) {
            IconButton(onClick = { onBackPress() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            }

        } else {

        }
    }, actions = {
        if (showDeleteNoteIcon) {
            IconButton(onClick = { onDeleteClick() }) {
                Icon(
                    imageVector = Icons.Filled.DeleteOutline,
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        if (showSubmitIcon) {
            IconButton(onClick = { onSubmitClick() }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }, backgroundColor = Color.White)
}