package com.example.jetpackcompose_material2_demo.ui.update_note

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpackcompose_material2_demo.data.model.ColorModel
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.ui.add_note.component.AppBarC
import com.example.jetpackcompose_material2_demo.ui.add_note.component.BGColorC
import com.example.jetpackcompose_material2_demo.ui.add_note.component.HobbyCheckBox
import com.example.jetpackcompose_material2_demo.ui.add_note.component.SwitchC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateNoteScreen(navController: NavHostController, id: String) {

    val maxTitleChar = 10
    val maxDescChar = 50

    val viewModel: UpdateNoteViewModel = hiltViewModel()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val noteModelState = viewModel.noteModel.collectAsState(initial = UpdateNoteState.Loading)
    when (val result = noteModelState.value) {

        UpdateNoteState.Loading -> {
            Log.d("UpdateNoteScreen", "Loading: ")
        }

        is UpdateNoteState.Success -> {

            var getNoteModel by rememberSaveable { mutableStateOf(result.model) }

            Column(modifier = Modifier.fillMaxSize()) {

                AppBarC(
                    title = "Update Note",
                    showBackArrow = true,
                    showSubmitIcon = getNoteModel.description.isNotBlank(),
                    onSubmitClick = {
                        try {
                            val getId = viewModel.updateNote(
                                NoteModel(
                                    id = getNoteModel.id,
                                    title = getNoteModel.title,
                                    description = getNoteModel.description,
                                    hobbies = viewModel.selectedHobbyList.toList().toString(),
                                    isImp = viewModel.markAsImp.value,
                                    color = viewModel.selectedColor.value.colorName,
                                    bgColor = viewModel.selectedColor.value.color.toString(),
                                )
                            )
                            coroutineScope.launch(Dispatchers.Main) {
                                getId.collect { id ->
                                    if (id > 0) {
                                        navController.popBackStack()
                                        Toast.makeText(
                                            context,
                                            "Note Updated successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.d("UpdateNoteScreen", "UpdateNoteScreen: ${e.stackTrace}")
                            Toast.makeText(context, "Error in updating note", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    onBackPress = {
                        navController.popBackStack()
                    }
                )

                OutlinedTextField(
                    value = getNoteModel.title,
                    singleLine = true,
                    onValueChange = { input ->
                        if (input.length <= maxTitleChar) {
                            getNoteModel = getNoteModel.copy(title = input)
                        }
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )

                OutlinedTextField(
                    value = getNoteModel.description,
                    onValueChange = { input ->
                        if (input.length <= maxDescChar) {
                            getNoteModel = getNoteModel.copy(description = input)
                        }
                    },
                    placeholder = {
                        Text(text = "Description")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )

                /*val colorList = remember {
                    mutableStateOf(viewModel.colorList)
                }.value.map {
                    it.copy(isSelected = getNoteModel.bgColor == it.colorName)
                }.toMutableList()*/

                BGColorC(
                    colorList = viewModel.colorList,
                    onClickEvent = { pos: Int, model: ColorModel ->

                        viewModel.changeColor(position = pos, model = model)
                    })

                val list = remember {
                    mutableStateOf(viewModel.hobbyCheckBoxList)
                }
                HobbyCheckBox(onCheckedEvent = { selectedHobby: String, isChecked: Boolean ->

                    if (isChecked) {
                        viewModel.addHobbyToList(selectedHobby)
                    } else {
                        viewModel.removeHobbyToList(selectedHobby)
                    }
//                    Log.d("UpdateNoteScreen", "selectedHobbyList: ${viewModel.selectedHobbyList.toList()}")
                }, list.value)

                SwitchC(onCheckedEvent = { isChecked ->
                    viewModel.switchEvent(isChecked)
                }, viewModel.markAsImp.collectAsState().value)
            }

        }

        is UpdateNoteState.Error -> {
            Log.d("UpdateNoteScreen", "Error: ${result.exception}")
            Toast.makeText(context, "Unable to load note detail", Toast.LENGTH_SHORT).show()
        }
    }
}