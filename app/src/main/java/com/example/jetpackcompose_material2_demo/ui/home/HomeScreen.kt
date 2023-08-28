package com.example.jetpackcompose_material2_demo.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpackcompose_material2_demo.ui.home.component.FloatingActionButtonC
import com.example.jetpackcompose_material2_demo.ui.home.component.HomeScreenContent
import com.example.jetpackcompose_material2_demo.ui.home.searchView.MainAppBar
import com.example.jetpackcompose_material2_demo.ui.home.searchView.SearchViewState
import com.example.jetpackcompose_material2_demo.util.Constants
import com.example.jetpackcompose_material2_demo.util.Constants.UPDATE_NOTE_SCREEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val searchViewState by viewModel.searchViewState
    val searchTextState by viewModel.searchTextState
    val context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainAppBar(
                isAnyNoteSelectedValue = viewModel.isAnyNoteSelected.value,
                searchViewState = searchViewState,
                searchText = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                    Log.d("HomeScreen", "onTextChange: $it")
                },
                onCloseClick = {
                    viewModel.updateSearchTextState(newValue = "")
                    viewModel.updateSearchViewState(newValue = SearchViewState.CLOSED)
                },
                onSearchClick = {

                    Log.d("HomeScreen", "onSearchClick: $it")
                },
                onSearchTriggered = {
                    viewModel.updateSearchViewState(newValue = SearchViewState.OPENED)
                },
                onMultipleDelete = {
                    val getId = viewModel.deleteMultipleNotes(true)
                    coroutineScope.launch(Dispatchers.Main) {
                        getId.collect { id ->
                            if (id > 0) {
                                viewModel.updateDeleteDoneValue(true)
                                Log.d("TAG", "deleteMultipleNotes: $id")
                                Toast.makeText(
                                    context, "Removed successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            )

        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                HomeScreenContent(onItemClick = { model ->

                    try {
                        if (viewModel.selectedTagChip.value == "" && viewModel.searchTextState.value == "") {
                            if (viewModel.isAnyNoteSelected.value) {

                                val getId = viewModel.updateNote(
                                    model.copy(isSelected = !model.isSelected)
                                )
                                coroutineScope.launch(Dispatchers.Main) {
                                    getId.collect { id ->
                                        if (id > 0) {
                                            //TODO done
                                        } else {
                                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                                if (!model.isSelected) {

                                    if (viewModel.selectedNoteCounter.value < viewModel.totalNoteCount.value) {

                                        val addCounter = viewModel.selectedNoteCounter.value + 1
                                        viewModel.updateSelectedNoteCounterValue(addCounter)
                                        if (viewModel.selectedNoteCounter.value == viewModel.totalNoteCount.value) {

                                            viewModel.updateIsAllNoteSelectedValue(true)
                                        }
                                    }
                                } else {
                                    val subCounter = viewModel.selectedNoteCounter.value - 1
                                    viewModel.updateSelectedNoteCounterValue(subCounter)
                                    if (viewModel.selectedNoteCounter.value == 0) {

//                                        Toast.makeText(context, "Last One", Toast.LENGTH_SHORT).show()
                                        viewModel.updateIsAnyNoteSelectedValue(false)
                                    }
                                }

                            } else {
                                navController.navigate("$UPDATE_NOTE_SCREEN/${model.id}")
                            }
                        }
                        else {
                            navController.navigate("$UPDATE_NOTE_SCREEN/${model.id}")
                        }
                    } catch (e: Exception) {

                        val error = Log.getStackTraceString(e)
                        Log.d("HomeScreen", "onItemClick: $error")
                    }

                }, onLongPress = { model ->

                    try {
                        if (viewModel.selectedTagChip.value == "" && viewModel.searchTextState.value == "") {
                            if (!viewModel.isAnyNoteSelected.value) {
                                val getId = viewModel.updateNote(
                                    model.copy(isSelected = !model.isSelected)
                                )
                                coroutineScope.launch(Dispatchers.Main) {
                                    getId.collect { id ->
                                        if (id > 0) {
                                            //TODO done
                                        } else {
                                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                }
                                viewModel.updateIsAnyNoteSelectedValue(true)
                                val addCounter = viewModel.selectedNoteCounter.value + 1
                                viewModel.updateSelectedNoteCounterValue(addCounter)
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("HomeScreen", "onLongPress: ${e.stackTrace}")
                        Toast.makeText(context, "Error in updating note", Toast.LENGTH_SHORT)
                            .show()
                    }
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