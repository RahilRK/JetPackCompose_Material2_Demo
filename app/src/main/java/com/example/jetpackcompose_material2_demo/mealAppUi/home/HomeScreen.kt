package com.example.jetpackcompose_material2_demo.mealAppUi.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.R
import com.example.jetpackcompose_material2_demo.mealAppUi.component.CategoryList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.HomeStaticSearch
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_SCREEN_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomeScreen() {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .background(Color.Gray.copy(alpha = 0.1f))
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ) {
            Row() {
                Column(modifier = Modifier.weight(0.7f)) {
                    Text(
                        text = "Hello, Lisa",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "What would you like to cook today?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Image(
                    painterResource(id = R.drawable.ic_lady),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically)
                        .border(1.dp, Color.White, CircleShape),
                )
            }

            HomeStaticSearch()

            val categoryListState =
                viewModel.categoryList.collectAsState(initial = HomeScreenState.Loading)
            when (val result = categoryListState.value) {
                HomeScreenState.Loading -> {
                    Log.d(HOME_SCREEN_TAG, "Loading...")
                }

                is HomeScreenState.Success -> {
                    Log.d(HOME_SCREEN_TAG, "Success:")

                    val listStatee = rememberLazyListState()

                    CategoryList(result.list, onClickEvent = { pos, model ->

                        viewModel.update(pos, model, result.list)
                    }, listStatee)
                }

                is HomeScreenState.Empty -> {
                    Log.d(HOME_SCREEN_TAG, "Empty: ")
                }

                is HomeScreenState.Error -> {
                    Log.e(HOME_SCREEN_TAG, "Error: $result")
                    Toast.makeText(context, "Unable to load category", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}