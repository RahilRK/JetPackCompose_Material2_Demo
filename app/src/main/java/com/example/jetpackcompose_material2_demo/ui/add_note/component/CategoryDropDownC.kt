package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.data.model.DropDownCategoryModel

@Preview
@Composable
fun CategoryDropDownC(
    list: MutableList<DropDownCategoryModel> = arrayListOf(),
    isExpanded: Boolean = false,
    onExpandCollapse: (value: Boolean) -> Unit = {},
    model: DropDownCategoryModel = DropDownCategoryModel(),
    onDropDownSelected: (value: DropDownCategoryModel) -> Unit = {},
) {

    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(5.dp),
                text = "Tag: ",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .clickable {
                        onExpandCollapse(!isExpanded)
                    }
                    .border(1.dp, color = Color.Black.copy(alpha = 0.2f), RoundedCornerShape(4.dp)),
            ) {
                Icon(
                    imageVector = model.imageVector,
                    contentDescription = null,
                    Modifier.padding(start = 8.dp, top = 5.dp),
                    tint = Color.Black.copy(alpha = 0.5f)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp),
                    text = model.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Icon(
                    modifier = Modifier.padding(5.dp),
                    imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null,
                    tint = Color.Black.copy(alpha = 0.3f)
                )


                DropdownMenu(expanded = isExpanded,
                    onDismissRequest = {
                        onExpandCollapse(false)
                    }) {

                    list.forEach {

                        DropdownMenuItem(
                            onClick = {
                                onDropDownSelected(it)
                                onExpandCollapse(false)
                            },
                        ) {
                            Text(text = it.title)
                        }

                    }

                }


            }
        }

    }
}