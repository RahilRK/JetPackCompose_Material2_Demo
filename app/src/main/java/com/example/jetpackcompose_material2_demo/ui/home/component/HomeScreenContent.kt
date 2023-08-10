package com.example.jetpackcompose_material2_demo.ui.home.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.ui.home.HomeViewModel
import com.example.jetpackcompose_material2_demo.ui.home.HomeViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomeScreenContent(onItemClick: (model: NoteModel) -> Unit = {},) {

    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val noteListState = viewModel.list.collectAsState(initial = HomeViewState.Loading)
    when (val result = noteListState.value) {
        HomeViewState.Loading -> {
            Log.d("HomeScreenContent", "Loading: ")
        }

        is HomeViewState.Success -> {
            Log.d("HomeScreenContent", "Success: ${result.task}")
            LazyColumn(content = {
                itemsIndexed(result.task) { index: Int, model: NoteModel ->
                    NoteListItem(model,
                        onDeleteClick = {
                        val getId = viewModel.deleteNote(model.id ?: -1)
                        coroutineScope.launch(Dispatchers.Main) {
                            getId.collect { id ->
                                if (id > 0) {
                                    Toast.makeText(
                                        context, "Note removed successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }, onItemClick = {
                        onItemClick(model)
                    })
                }
            })
        }

        is HomeViewState.Empty -> {
            Log.d("HomeScreenContent", "Empty: ")
        }

        is HomeViewState.Error -> {
            Log.d("HomeScreenContent", "Error: ${result.exception}")
            Toast.makeText(context, "Unable to load notes", Toast.LENGTH_SHORT).show()
        }
    }
}


@Preview
@Composable
fun NoteListItem(
    noteModel: NoteModel = NoteModel(title = "title", description = "description"),
    onDeleteClick: () -> Unit = {},
    onItemClick: (model: NoteModel) -> Unit = {},
) {
    Card(
        border = BorderStroke(width = 0.5.dp, color = Color.Gray),
        elevation = 4.dp,
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onItemClick(noteModel)
            }, backgroundColor = Color.White, shape = RoundedCornerShape(4.dp)
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(100.dp)
            ) {
                Text(text = noteModel.title, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = noteModel.description, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            }
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .size(32.dp)
                    .clickable {
                        onDeleteClick()
                    },
                imageVector = Icons.Filled.DeleteOutline,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red)
            )
        }
    }
}