package com.example.jetpackcompose_material2_demo.mealAppUi.component

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose_material2_demo.mealNavigation.NavigationController
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import com.example.jetpackcompose_material2_demo.util.Constants.COUNTRY_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.INGREDIENTS_MEAL_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.Navigation_TAG
import com.example.jetpackcompose_material2_demo.util.NavigationItem

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: HOME_ROUTE

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.SearchMeal,
        NavigationItem.CountryMeal,
        NavigationItem.IngredientsMeal
    )

    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colors.background) {

                items.forEach {
                    BottomNavigationItem(
                        selected = currentRoute == it.route,
                        onClick = {
/*
                            if(currentRoute != it.route) {

                                navController.graph.startDestinationRoute?.let { route ->

                                    Log.d("BottomNavigationBar", "startDestinationRoute: $route")

                                    navController.popBackStack(route = route, true)

                                    navController.navigate(route = route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
*/
                            navController.navigate(it.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    Log.d(Navigation_TAG, "startDestinationRoute: ${it.route}")

                                    /*if ((it.route == HOME_ROUTE) ||
                                        (it.route == COUNTRY_MEAL_ROUTE) ||
                                        (it.route == INGREDIENTS_MEAL_ROUTE)
                                    ) {
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }*/

                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = it.icons,
                                contentDescription = null,
                                tint = if (currentRoute == it.route) meal_color_primary else Color.LightGray
                            )
                        },
                        label = {
                            Text(
                                text = it.label,
                                color = if (currentRoute == it.route) meal_color_primary else Color.LightGray
                            )
                        })
                }
            }
        }
    ) {
        NavigationController(navController = navController, it)
    }
}