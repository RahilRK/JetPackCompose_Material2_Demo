package com.example.jetpackcompose_material2_demo.ui.add_note

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.ui.add_note.component.AppBarC
import com.example.jetpackcompose_material2_demo.ui.add_note.component.HobbyCheckBox
import com.example.jetpackcompose_material2_demo.ui.add_note.component.SwitchC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddNoteScreen(navController: NavHostController = rememberNavController()) {

    var getNoteModel by rememberSaveable { mutableStateOf(NoteModel()) }

    val maxTitleChar = 10
    val maxDescChar = 50

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val viewModel: AddNoteViewModel = hiltViewModel()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {

        AppBarC(
            title = "Add Note",
            showBackArrow = true,
            showSubmitIcon = getNoteModel.description.isNotBlank(),
            onSubmitClick = {
                try {
                    val getId = viewModel.saveNote(
                        NoteModel(
                            title = getNoteModel.title,
                            description = getNoteModel.description,
                            hobbies = viewModel.selectedHobbyList.toList().toString(),
                            isImp = viewModel.markAsImp.value
                        )
                    )
                    coroutineScope.launch(Dispatchers.Main) {
                        getId.collect { id ->
                            if (id > 0) {
                                navController.popBackStack()
                                Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("AddNoteScreen", "AddNoteScreen: ${e.stackTrace}")
                    Toast.makeText(context, "Error in adding note", Toast.LENGTH_SHORT).show()
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
            /*label = { Text(text = "Enter your name") },*/
            placeholder = {
                Text(text = "Title")
            },
            /*supportingText = {
                Text(text = "Invalid name")
            },
            isError = true*/
            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Decimal,
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
//                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )

        val list = remember {
            mutableStateOf(viewModel.hobbyCheckBoxList)
        }
        HobbyCheckBox(onCheckedEvent = {selectedHobby: String, isChecked: Boolean ->

            if(isChecked) {
                viewModel.addHobbyToList(selectedHobby)
            }
            else {
                viewModel.removeHobbyToList(selectedHobby)
            }
//            Log.d("AddNoteScreen", "selectedHobbyList: ${viewModel.selectedHobbyList.toList()}")
        }, list.value)


        SwitchC(onCheckedEvent = {isChecked ->
            viewModel.switchEvent(isChecked)
        }, viewModel.markAsImp.collectAsState().value)
    }
}