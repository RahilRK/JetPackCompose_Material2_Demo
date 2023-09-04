package com.example.jetpackcompose_material2_demo.mealNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcompose_material2_demo.mealAppUi.country_meal.CountryMealScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.home.HomeScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal.IngredientsMealScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.SearchMealScreen
import com.example.jetpackcompose_material2_demo.util.Constants.COUNTRY_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.INGREDIENTS_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.SEARCH_MEAL_SCREEN_ROUTE

@Composable
fun NavigationController(
    navController: NavHostController,
    paddingValues: PaddingValues,
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
//        startDestination = SEARCH_MEAL_SCREEN_ROUTE,
    ) {

        composable(HOME_ROUTE) {
            HomeScreen(navController, hideBottomNav = hideBottomNav, onScrollEvent = onScrollEvent)
        }

        composable(SEARCH_MEAL_SCREEN_ROUTE) {
            SearchMealScreen()
        }

        composable(COUNTRY_MEAL_ROUTE) {
            CountryMealScreen(navController, hideBottomNav = hideBottomNav, onScrollEvent = onScrollEvent)
        }

        composable(INGREDIENTS_MEAL_ROUTE) {
            IngredientsMealScreen(navController, hideBottomNav = hideBottomNav, onScrollEvent = onScrollEvent)
        }
    }
}