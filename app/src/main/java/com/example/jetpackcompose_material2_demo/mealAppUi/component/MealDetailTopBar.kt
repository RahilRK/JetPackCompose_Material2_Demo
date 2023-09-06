package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MealDetailTopBar(onBackPress: () -> Unit = {}) {
    TopAppBar(title = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 6.dp),
        navigationIcon = {
            Image(
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.White)
                    .padding(6.dp)
                    .clickable {
                        onBackPress()
                    },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.DarkGray
                )
            )
        }, actions = {
            Image(
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.White)
                    .padding(6.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.Red
                )
            )
        }, backgroundColor = Color.Transparent, elevation = 0.dp
    )
}