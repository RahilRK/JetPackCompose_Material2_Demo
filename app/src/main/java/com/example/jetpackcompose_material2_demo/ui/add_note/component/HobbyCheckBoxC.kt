package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.data.model.HobbyModel

@Preview
@Composable
fun HobbyCheckBox(
    onCheckedEvent: (selectedHobby: String, isChecked: Boolean) -> Unit = { s: String, b: Boolean -> },
    hobbyCheckBoxList: MutableList<HobbyModel> = arrayListOf(),
) {

    /*val hobbyCheckBoxList = remember {
        mutableStateListOf(
            HobbyModel(title = "Cricket", isChecked = false),
            HobbyModel(title = "Volleyball", isChecked = false),
            HobbyModel(title = "Video game", isChecked = false)
        )
    }*/

    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {

        Text(
            text = "Select hobby:",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        hobbyCheckBoxList.forEachIndexed { index: Int, model: HobbyModel ->
            Row(verticalAlignment = Alignment.CenterVertically) {

                Checkbox(
                    checked = model.isChecked,
                    onCheckedChange = { isChecked ->
                        hobbyCheckBoxList[index] = model.copy(
                            isChecked = isChecked
                        )
                        onCheckedEvent(model.title, isChecked)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary
                    )
                )
                Text(
                    text = model.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}