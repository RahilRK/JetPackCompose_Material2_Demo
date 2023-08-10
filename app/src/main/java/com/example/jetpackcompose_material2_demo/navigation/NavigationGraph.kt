package com.example.jetpackcompose_material2_demo.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose_material2_demo.ui.add_note.AddNoteScreen
import com.example.jetpackcompose_material2_demo.ui.home.HomeScreen
import com.example.jetpackcompose_material2_demo.ui.update_note.UpdateNoteScreen
import com.example.jetpackcompose_material2_demo.util.Constants.ADD_NOTE_SCREEN
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_SCREEN
import com.example.jetpackcompose_material2_demo.util.Constants.UPDATE_NOTE_SCREEN

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HOME_SCREEN) {
        composable(route = HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(route = ADD_NOTE_SCREEN) {
            AddNoteScreen(navController)
        }
        composable(route = "$UPDATE_NOTE_SCREEN/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            val id = backStackEntry.arguments!!.getString("id")
            Log.d("NavigationGraph", "UpdateNoteScreen id: $id")
            UpdateNoteScreen(navController, id ?: "-1")
        }
    }
}