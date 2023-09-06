package com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberSwipeableState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.R
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealDetailIngredientList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.ExpandingText
import com.example.jetpackcompose_material2_demo.mealAppUi.component.MealDetailIngredientListItem
import com.example.jetpackcompose_material2_demo.mealAppUi.component.MealDetailTopBar
import com.example.jetpackcompose_material2_demo.ui.theme.gold_color
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MealDetailScreen(onBackPress: () -> Unit = {}) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val imageHeight = configuration.screenHeightDp / 2.5
    val peekHeight = configuration.screenHeightDp / 1.6
    val list: MutableList<MealDetailIngredientList> = arrayListOf(
        MealDetailIngredientList(strIngredient = "soy sauce", strMeasure = "3/4 cup"),
        MealDetailIngredientList(strIngredient = "water", strMeasure = "1/2 cup"),
        MealDetailIngredientList(strIngredient = "water", strMeasure = "1/4 cup"),
        MealDetailIngredientList(strIngredient = "ground ginger", strMeasure = "1/2 teaspoon"),
        MealDetailIngredientList(strIngredient = "minced garlic", strMeasure = "1/2 teaspoon"),
        MealDetailIngredientList(strIngredient = "cornstarch", strMeasure = "4 Tablespoons"),
    )
    val context = LocalContext.current

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MealDetailTopBar(onBackPress = onBackPress)
        },
        sheetContent = {
            //Bottom sheet content
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
                                    text = "Chicken",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                Row {
                                    Image(
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        imageVector = Icons.Outlined.WatchLater,
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(Color.Gray)
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 4.dp),
                                        text = "15 min",
                                        color = Color.Gray,
                                        fontSize = 18.sp,
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
                            text = "Preheat oven to 350° F. Spray a 9x13-inch baking pan with non-stick spray.\\r\\nCombine soy sauce, ½ cup water, brown sugar, ginger and garlic in a small saucepan and cover. Bring to a boil over medium heat. Remove lid and cook for one minute once boiling.\\r\\nMeanwhile, stir together the corn starch and 2 tablespoons of water in a separate dish until smooth. Once sauce is boiling, add mixture to the saucepan and stir to combine. Cook until the sauce starts to thicken then remove from heat.\\r\\nPlace the chicken breasts in the prepared pan. Pour one cup of the sauce over top of chicken. Place chicken in oven and bake 35 minutes or until cooked through. Remove from oven and shred chicken in the dish using two forks.\\r\\n*Meanwhile, steam or cook the vegetables according to package directions.\\r\\nAdd the cooked vegetables and rice to the casserole dish with the chicken. Add most of the remaining sauce, reserving a bit to drizzle over the top when serving. Gently toss everything together in the casserole dish until combined. Return to oven and cook 15 minutes. Remove from oven and let stand 5 minutes before serving. Drizzle each serving with remaining sauce. Enjoy!"
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
                                text = "6 item",
                                color = Color.LightGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    itemsIndexed(list) { index, model ->
                        MealDetailIngredientListItem(model)
                    }

                    /*
                                        item {
                                            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                                ExtendedFloatingActionButton(
                                                    modifier = Modifier.padding(top = 16.dp),
                                                    text = { Text(text = "Watch Videos", color = Color.White) },
                                                    onClick = {
                                                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                                                    },
                                                    icon = { Icon(Icons.Outlined.PlayCircle, "", tint = Color.White) }
                                                )
                                            }
                                        }
                    */
                }

            }
        },
        sheetBackgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,
        sheetPeekHeight = peekHeight.dp,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
            }, backgroundColor = meal_color_primary) {
                Icon(Icons.Outlined.PlayCircle, contentDescription = null, tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        //main screen content
        Image(
            painter = painterResource(id = R.drawable.ic_pasta),
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .height(imageHeight.dp)
                .offset(x = 0.dp, y = (-56).dp),
            contentScale = ContentScale.Crop,
        )
    }

}