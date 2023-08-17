package com.example.jetpackcompose_material2_demo.data.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.example.jetpackcompose_material2_demo.ui.theme.green
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorModel(
    var colorName: String = "",
    var color: ULong = green.value,
    var isSelected: Boolean = false
) : Parcelable
