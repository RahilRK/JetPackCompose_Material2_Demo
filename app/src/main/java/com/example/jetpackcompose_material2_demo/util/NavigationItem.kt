package com.example.jetpackcompose_material2_demo.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetpackcompose_material2_demo.util.Constants.COUNTRY_MEAL_LABEL
import com.example.jetpackcompose_material2_demo.util.Constants.COUNTRY_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_LABEL
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.INGREDIENTS_MEAL_LABEL
import com.example.jetpackcompose_material2_demo.util.Constants.INGREDIENTS_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.SEARCH_MEAL_LABEL
import com.example.jetpackcompose_material2_demo.util.Constants.SEARCH_MEAL_SCREEN_ROUTE


sealed class NavigationItem(val route: String, val label: String, val icons: ImageVector) {

    object Home :
        NavigationItem(route = HOME_ROUTE, label = HOME_LABEL, icons = Icons.Filled.Home)

    object SearchMeal : NavigationItem(
        route = SEARCH_MEAL_SCREEN_ROUTE,
        label = SEARCH_MEAL_LABEL,
        icons = Icons.Filled.Search
    )
    object CountryMeal : NavigationItem(
        route = COUNTRY_MEAL_ROUTE,
        label = COUNTRY_MEAL_LABEL,
        icons = Icons.Filled.LocationOn
    )

    object IngredientsMeal : NavigationItem(
        route = INGREDIENTS_MEAL_ROUTE,
        label = INGREDIENTS_MEAL_LABEL,
        icons = Icons.Filled.Fastfood
    )
}