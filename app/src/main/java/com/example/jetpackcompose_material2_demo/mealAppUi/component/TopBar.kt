package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TopBar(navigationIcon: ImageVector = Icons.Outlined.LocationOn, title: String = "Choose Area") {
    TopAppBar(title = {
        Text(text = title, color = Color.Black)
    }, navigationIcon = {
        IconButton(onClick = {

        }) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = "",
                tint = Color.Black
            )
        }

    }, backgroundColor = Color.White, elevation = 4.dp)
}