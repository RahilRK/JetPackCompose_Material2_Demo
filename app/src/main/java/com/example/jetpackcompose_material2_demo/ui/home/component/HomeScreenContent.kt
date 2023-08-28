package com.example.jetpackcompose_material2_demo.ui.home.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.data.model.DropDownCategoryModel
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.ui.home.HomeViewModel
import com.example.jetpackcompose_material2_demo.ui.home.HomeViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomeScreenContent(onItemClick: (model: NoteModel) -> Unit = {}, onLongPress: (model: NoteModel) -> Unit = {}) {

    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val noteListState = viewModel.list.collectAsState(initial = HomeViewState.Loading)
    when (val result = noteListState.value) {
        HomeViewState.Loading -> {
            Log.d("HomeScreenContent", "Loading: ")
        }

        is HomeViewState.Success -> {
            Log.d("HomeScreenContent", "Success: ${result.listType}")

            Column {
                TagFilterChipsC(
                    list = viewModel.tagChipList,
                    totalNoteCount = viewModel.totalNoteCount.value,
                    onChipClickEvent = { pos: Int, model: DropDownCategoryModel ->

                        viewModel.selectTagChip(position = pos, model = model)
                    })

                LazyColumn(
                    state = rememberLazyListState(),
                    content = {
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
                                }, onLongPress = {
                                    onLongPress(model)
                                },
                                viewModel.selectedTagChip.value,
                                viewModel.searchTextState.value,
                                viewModel.isAnyNoteSelected.value
                                )
                        }
                    })
            }
        }

        is HomeViewState.Empty -> {
            Log.d("HomeScreenContent", "Empty: ")
            TagFilterChipsC(
                list = viewModel.tagChipList,
                totalNoteCount = viewModel.totalNoteCount.value,
                onChipClickEvent = { pos: Int, model: DropDownCategoryModel ->

                    viewModel.selectTagChip(position = pos, model = model)
                })
        }

        is HomeViewState.Error -> {
            Log.e("HomeScreenContent", "Error: ${Log.getStackTraceString(result.exception)}")
            Toast.makeText(context, "Unable to load notes", Toast.LENGTH_SHORT).show()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun NoteListItem(
    noteModel: NoteModel = NoteModel(title = "title", description = "description"),
    onDeleteClick: () -> Unit = {},
    onItemClick: (model: NoteModel) -> Unit = {},
    onLongPress: (model: NoteModel) -> Unit = {},
    selectedTag: String = "",
    searchKeyWord: String = "",
    isAnyNoteSelected: Boolean = false,
) {
    Card(
//        border = BorderStroke(width = 0.5.dp, color = Color.Gray),
        elevation = 4.dp,
        modifier = Modifier
            .padding(6.dp)
            .combinedClickable(
                onClick = { onItemClick(noteModel) },
                onLongClick = { onLongPress(noteModel) },
            ), backgroundColor = Color.White, shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(noteModel.bgColor.toULong()))
//                .background(Color.Blue)
                .height(100.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {

                if(searchKeyWord == "") {
                    if(noteModel.isSelected && selectedTag == "") {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            Modifier
                                .size(42.dp)
                                .align(
                                    Alignment.CenterVertically
                                )
                                .padding(start = 6.dp),
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically).padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = noteModel.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = noteModel.description,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
            }
            /*
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
            */
        }
    }
}
