package com.example.jetpackcompose_material2_demo.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpackcompose_material2_demo.ui.add_note.component.AppBarC
import com.example.jetpackcompose_material2_demo.ui.home.component.FloatingActionButtonC
import com.example.jetpackcompose_material2_demo.ui.home.component.HomeScreenContent
import com.example.jetpackcompose_material2_demo.ui.home.searchView.MainAppBar
import com.example.jetpackcompose_material2_demo.ui.home.searchView.SearchViewState
import com.example.jetpackcompose_material2_demo.util.Constants
import com.example.jetpackcompose_material2_demo.util.Constants.UPDATE_NOTE_SCREEN

@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val searchViewState by viewModel.searchViewState
    val searchTextState by viewModel.searchTextState

    Scaffold(
        topBar = {
            MainAppBar(
                searchViewState = searchViewState,
                searchText = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                    Log.d("Home", "onTextChange: $it")
                },
                onCloseClick = {
                    viewModel.updateSearchTextState(newValue = "")
                    viewModel.updateSearchViewState(newValue = SearchViewState.CLOSED)
                },
                onSearchClick = {

                    Log.d("Home", "onSearchClick: $it")
                },
                onSearchTriggered = {
                    viewModel.updateSearchViewState(newValue = SearchViewState.OPENED)
                }
            )

        },
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                HomeScreenContent(onItemClick = { model->
                    navController.navigate("$UPDATE_NOTE_SCREEN/${model.id}")
                })
            }
        },
        floatingActionButton = {
            FloatingActionButtonC(onClick = {
                navController.navigate(Constants.ADD_NOTE_SCREEN)
            })
        }
    )

}