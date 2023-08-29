package com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_SCREEN_TAG
import com.example.jetpackcompose_material2_demo.util.Constants.INGREDIENTS_MEAL_SCREEN_TAG

@Preview
@Composable
fun IngredientsMealScreen() {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = INGREDIENTS_MEAL_SCREEN_TAG, fontSize = 24.sp)
    }
}