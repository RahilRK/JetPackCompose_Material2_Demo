package com.example.jetpackcompose_material2_demo.ui.home.searchView

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainAppBar(
    isAnyNoteSelectedValue: Boolean,
    searchViewState: SearchViewState,
    searchText: String,
    onTextChange: (String) -> Unit = {},
    onCloseClick: () -> Unit = {},
    onSearchClick: (String) -> Unit = {},
    onSearchTriggered: () -> Unit = {},
    onMultipleDelete: () -> Unit = {},
) {
    if (searchViewState == SearchViewState.CLOSED) {
        DefaultAppBar(
            isAnyNoteSelectedValue = isAnyNoteSelectedValue,
            onSearchClicked = onSearchTriggered,
            onMultipleDelete = onMultipleDelete,
        )
    } else if (searchViewState == SearchViewState.OPENED) {
        SearchAppBar(
            text = searchText,
            onTextChange = onTextChange,
            onCloseClick = onCloseClick,
            onSearchClick = onSearchClick,
        )
    }
}

@Composable
fun DefaultAppBar(
    isAnyNoteSelectedValue: Boolean,
    onSearchClicked: () -> Unit = {},
    onMultipleDelete: () -> Unit = {}
) {
    TopAppBar(title = {
        Text(text = "Home")
    }, actions = {
        IconButton(onClick = {
            onSearchClicked()
        }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
            )
        }

        if (isAnyNoteSelectedValue) {
            IconButton(onClick = {
                onMultipleDelete()
            }) {
                Icon(
                    imageVector = Icons.Filled.DeleteOutline,
                    contentDescription = "Deleted Icon",
                    tint = Color.Red
                )
            }
        }
    }, contentColor = Color.Black, backgroundColor = Color.White)
}

@Preview
@Composable
fun DefaultAppBarPreview() {
    DefaultAppBar(isAnyNoteSelectedValue = false, onSearchClicked = {

    })
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = Color.White,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 5.dp, end = 5.dp),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    text = "Search by title or description",
                    color = Color.Black.copy(alpha = 0.5f)
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.body1.fontSize,
                color = Color.Black
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        tint = Color.Black.copy(alpha = 0.5f)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClick()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Black.copy(alpha = 0.05f),
                cursorColor = Color.Black.copy(alpha = 0.5f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(
                32.dp
            )

        )
    }
}

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        {},
        {},
        {}
    )
}