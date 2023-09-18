package com.example.jetpackcompose_material2_demo.mealNavigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_ROUTE
import com.example.jetpackcompose_material2_demo.util.Constants.Navigation_TAG
import com.example.jetpackcompose_material2_demo.util.Constants.currentScreen
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

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    navController.addOnDestinationChangedListener { navController, destination, bundle ->
        destination.route?.let {
            Log.d(
                Navigation_TAG,
                "addOnDestinationChangedListener: ${it}"
            )
            bottomBarState.value = !it.contains("meal_detail")
            currentScreen = it
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
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
                                            Log.d(
                                                Navigation_TAG,
                                                "startDestinationRoute: ${it.route}"
                                            )

                                            if(currentScreen != "search_meal") {
                                                popUpTo(route) {
                                                    saveState = true
                                                }
                                            }
                                            else {
                                                popUpTo(route) {
                                                    saveState = false
                                                }
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
            )
        }
    ) {
        NavigationController(
            navController = navController,
            it,
            hideBottomNav = bottomBarState.value,
            hideBottomNavEvent = {
                bottomBarState.value = it
                Log.d(Navigation_TAG, "Navigation: $it")
            })
    }
}