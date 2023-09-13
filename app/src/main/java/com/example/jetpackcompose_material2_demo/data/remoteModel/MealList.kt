package com.example.jetpack_compose_demo.data.model

import com.example.jetpackcompose_material2_demo.data.remoteModel.Meal

data class MealList(
    var meals: List<Meal>? = listOf()
)