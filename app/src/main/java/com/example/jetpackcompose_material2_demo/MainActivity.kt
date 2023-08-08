package com.example.jetpackcompose_material2_demo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.jetpackcompose_material2_demo.ui.theme.JetPackCompose_Material2_DemoTheme
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackCompose_Material2_DemoTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }*/

                AlertDialogC()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun BottomSheetC() {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            //Bottom sheet content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Bottom Sheet", fontSize = 60.sp, color = Color.White)
            }
        },
        sheetBackgroundColor = MaterialTheme.colors.primary,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        //main screen content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if (bottomSheetState.isExpanded) {
                        bottomSheetState.collapse()
                    } else {
                        bottomSheetState.expand()
                    }
                }
            }) {
                Text(text = "Toggle Sheet")
            }
        }
    }
}

@Preview
@Composable
fun ConstrainC() {

    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenBox")
        val redBox = createRefFor("redBox")
        val guideLine = createGuidelineFromTop(0.10f)

        constrain(greenBox) {
//            top.linkTo(parent.top)
            top.linkTo(guideLine)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        constrain(redBox) {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Spread)
    }

    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenBox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redBox")
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ViewPagerC() {

    val pagerState = rememberPagerState()

    Column {
        Box() {
            HorizontalPager(
                pageCount = viewPagerDataList.size,
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { position ->

                ViewPagerItemC(viewPagerData = viewPagerDataList[position])
            }

            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(viewPagerDataList.size) { pos ->
                    val color = if (pagerState.currentPage == pos)
                        Color.White else
                        Color.Black
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(15.dp)

                    )
                }
            }

        }

    }
}

@Composable
fun ViewPagerItemC(viewPagerData: ViewPagerData) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .background(viewPagerData.color)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = viewPagerData.text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp
            )
        }
    }
}

data class ViewPagerData(
    val color: Color,
    val text: String
)

val viewPagerDataList = listOf<ViewPagerData>(
    ViewPagerData(Color.Blue, "Page 1"),
    ViewPagerData(Color.Green, "Page 2"),
    ViewPagerData(Color.Red, "Page 3"),
)

@Preview
@Composable
fun AlertDialogC() {

    val dialogueState = remember { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { dialogueState.value = true }) {
            Text(text = "Open Dialogue")
        }
    }

    if (dialogueState.value) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { /*TODO*/ },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(onClick = { dialogueState.value = false }) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = { dialogueState.value = false }) {
                        Text(text = "OK")
                    }
                }
            }, title = { Text(text = "Hold on") }, text = { Text(text = "Please wait...") })
    }
}

@Preview
@Composable
fun DatePickerC() {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    var selectedDateText by remember { mutableStateOf("") }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

//    If you want to disable past dates you can set a minDate.
    datePicker.datePicker.minDate = calendar.timeInMillis

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (selectedDateText.isNotEmpty()) {
                "Selected date is $selectedDateText"
            } else {
                "Please pick a date"
            }
        )

        Button(
            onClick = {
                datePicker.show()
            }
        ) {
            Text(text = "Select a date")
        }
    }
}

@Preview
@Composable
fun TimePickerC() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Fetching current hour, and minute
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    var selectedTimeText by remember { mutableStateOf("") }

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, false
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (selectedTimeText.isNotEmpty()) {
                "Selected time is $selectedTimeText"
            } else {
                "Please select the time"
            }
        )

        Button(
            onClick = {
                timePicker.show()
            }
        ) {
            Text(text = "Select time")
        }
    }
}