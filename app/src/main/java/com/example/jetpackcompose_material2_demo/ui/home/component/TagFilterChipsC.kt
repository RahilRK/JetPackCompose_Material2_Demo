package com.example.jetpackcompose_material2_demo.ui.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose_material2_demo.data.model.ColorModel
import com.example.jetpackcompose_material2_demo.data.model.DropDownCategoryModel

@Composable
fun TagFilterChipsC(
    list: MutableList<DropDownCategoryModel> = arrayListOf(),
    totalNoteCount: Int = 0,
    onChipClickEvent: (pos: Int, model: DropDownCategoryModel) -> Unit = { pos: Int, model: DropDownCategoryModel -> },
) {
    LazyRow(content = {
        itemsIndexed(list) { index, model ->

            TagFilterChipsItemC(index, totalNoteCount, model, onChipClickEvent = onChipClickEvent)
        }
    }, modifier = Modifier.padding(4.dp))

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun TagFilterChipsItemC(
    index: Int = -1,
    totalNoteCount: Int = 0,
    model: DropDownCategoryModel = DropDownCategoryModel(isSelected = true),
    onChipClickEvent: (pos: Int, model: DropDownCategoryModel) -> Unit = { pos: Int, model: DropDownCategoryModel -> }
) {
    FilterChip(
        modifier = Modifier.padding(10.dp),
        selected = model.isSelected,
        colors = ChipDefaults.filterChipColors(
            backgroundColor = if (model.isSelected)
                Color.Black
            else
                Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        border = if (model.isSelected)
            BorderStroke(width = 1.dp, color = Color.Transparent)
        else
            BorderStroke(width = 1.dp, color = Color.Black),
        onClick = {
            val getModelData = model.copy(isSelected = !model.isSelected)
            onChipClickEvent(index, getModelData)
        },
        content = {
            Text(
                text = if (index == 0)
                    "${model.title} (${totalNoteCount})"
                else
                    model.title,
                color = if (model.isSelected)
                    Color.White
                else
                    Color.Black
            )
        }, leadingIcon = {
            Icon(
                imageVector = model.imageVector,
                contentDescription = null,
                tint = if (model.isSelected)
                    Color.White
                else
                    Color.Black
            )
        })
}