package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.data.model.ColorModel


@Preview
@Composable
fun BGColorC(
    colorList: MutableList<ColorModel> = arrayListOf(),
    onClickEvent: (pos: Int, model: ColorModel) -> Unit = { pos: Int, model: ColorModel -> },
) {
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
        Text(
            text = "Select note color:",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        LazyRow(content = {
            itemsIndexed(colorList) { index, model ->

                BGColorItemC(index, model, onClickEvent)
            }
        }, modifier = Modifier.padding(4.dp))
    }
}

@Preview
@Composable
fun BGColorItemC(
    index: Int = -1,
    model: ColorModel? = null,
    onClickEvent: (pos: Int, model: ColorModel) -> Unit = { pos: Int, model: ColorModel -> },
) {

    model?.let {
        val borderColor = if (it.isSelected) {
            Color.Black
        } else {
            Color(it.color)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = CircleShape)
                    .background(Color(model.color))
                    .clickable {
                        val getModelData = model.copy(isSelected = !model.isSelected)
                        onClickEvent(index, getModelData)
                    }
                    .border(width = 2.dp, color = borderColor, shape = CircleShape)
            )
        }
    }
}
