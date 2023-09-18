package com.example.jetpackcompose_material2_demo.mealAppUi.component

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealDetailIngredientList
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealX
import com.example.jetpackcompose_material2_demo.ui.theme.gold_color
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MealDetailScreenContent(
    onBackPress: () -> Unit = {},
    mLoadingDialogueState: Boolean = false,
    model: MealX = MealX()
) {

    val context = LocalContext.current
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val imageHeight = configuration.screenHeightDp / 2.5
    val peekHeight = configuration.screenHeightDp / 1.6
    val list: List<MealDetailIngredientList> = model.mealIngredientList

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MealDetailTopBar(onBackPress = onBackPress)
        },
        sheetContent = {
            //Bottom sheet content
            Box {
                Card(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .fillMaxWidth()
                    ) {
                        item {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Divider(
                                    color = Color.LightGray.copy(alpha = 0.2f),
                                    thickness = 6.dp,
                                    modifier = Modifier
                                        .width(72.dp)
                                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(16.dp))
                                        .clickable {
                                            scope.launch {
                                                if (bottomSheetState.isExpanded) {
                                                    bottomSheetState.collapse()
                                                } else {
                                                    bottomSheetState.expand()
                                                }
                                            }
                                        }
                                )
                            }
                        }


                        item {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(modifier = Modifier.weight(0.8f)) {
                                    Text(
                                        text = model.strMeal!!,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    Row {
                                        Image(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp),
                                            imageVector = Icons.Outlined.WatchLater,
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(Color.Gray)
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 4.dp),
                                            text = "15 min",
                                            color = Color.Gray,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }

                                }

                                Row(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .weight(0.3f, fill = true),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Image(
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(gold_color)
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 4.dp),
                                        text = "4.8",
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        item {
                            Text(
                                modifier = Modifier.padding(top = 12.dp),
                                text = "Description",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        item {
                            ExpandingText(
                                text = model.strInstructions!!
                            )
                        }

                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .align(Alignment.CenterStart),
                                    text = "Ingredients",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                        .align(Alignment.CenterEnd),
                                    text = "${
                                        model.mealIngredientList.filter {
                                            it.strIngredient != "" && it.strMeasure != ""
                                        }.size
                                    } item",
                                    color = Color.LightGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        itemsIndexed(list) { index, model ->
                            MealDetailIngredientListItem(model)
                        }
                    }
                }

                if (mLoadingDialogueState) {
                    LoadingDialog()
                }
            }
        },
        sheetBackgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,
        sheetPeekHeight = peekHeight.dp,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(model.strYoutube)
                    intent.setPackage("com.google.android.youtube")
                    startActivity(context, intent, null)
                } catch (e: Exception) {
                    Toast.makeText(context, "Unable to open youtube app", Toast.LENGTH_SHORT).show()
                }
            }, backgroundColor = meal_color_primary) {
                Icon(Icons.Outlined.PlayCircle, contentDescription = null, tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        //main screen content
        AsyncImage(
            model = model.strMealThumb,
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .height(imageHeight.dp)
                .offset(x = 0.dp, y = (-56).dp),
            contentScale = ContentScale.Crop,
        )
    }

}