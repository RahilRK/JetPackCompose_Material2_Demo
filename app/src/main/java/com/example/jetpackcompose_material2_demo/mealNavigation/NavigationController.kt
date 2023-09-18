package com.example.jetpackcompose_material2_demo.mealNavigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail.MealDetailScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.country_meal.CountryMealScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.home.HomeScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal.IngredientsMealScreen
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.SearchMealScreen
import com.example.jetpackcompose_material2_demo.util.Constants.COUNTRY_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.INGREDIENTS_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.MEAL_DETAIL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.SEARCH_MEAL_SCREEN_ROUTE

@Composable
fun NavigationController(
    navController: NavHostController,
    paddingValues: PaddingValues,
    hideBottomNav: Boolean = false,
    hideBottomNavEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    val TAG = "NavigationController"
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
    ) {

        composable(HOME_ROUTE) {
            HomeScreen(onSearchClick = {
                navController.navigate(SEARCH_MEAL_SCREEN_ROUTE)
            }, onListItemClick = { model ->
                navController.navigate("$MEAL_DETAIL_ROUTE/${model.idMeal}")
            }, hideBottomNav = hideBottomNav, hideBottomNavEvent = hideBottomNavEvent)
        }

        composable(SEARCH_MEAL_SCREEN_ROUTE) {
            SearchMealScreen(onListItemClick = { model ->
                navController.navigate("$MEAL_DETAIL_ROUTE/${model.idMeal}")
            })
        }

        composable(COUNTRY_MEAL_ROUTE) {
            CountryMealScreen(
                onListItemClick = { model ->
                    navController.navigate("$MEAL_DETAIL_ROUTE/${model.idMeal}")
                },
                hideBottomNav = hideBottomNav,
                onScrollEvent = hideBottomNavEvent
            )
        }

        composable(INGREDIENTS_MEAL_ROUTE) {
            IngredientsMealScreen(
                onListItemClick = { model ->
                    navController.navigate("$MEAL_DETAIL_ROUTE/${model.idMeal}")
                },
                hideBottomNav = hideBottomNav,
                onScrollEvent = hideBottomNavEvent
            )
        }

        composable("$MEAL_DETAIL_ROUTE/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            val id = backStackEntry.arguments!!.getString("id")
            Log.d(TAG, "MealDetailScreen id: $id")
            MealDetailScreen(onBackPress = {
                navController.popBackStack()
            })
        }
    }
}