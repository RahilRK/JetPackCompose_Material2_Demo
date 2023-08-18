package com.example.jetpackcompose_material2_demo.data.model

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetpackcompose_material2_demo.ui.theme.green
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DropDownCategoryModel(
    var imageVector: @RawValue ImageVector = Icons.Outlined.Person,
    var title: String = "Miscellaneous",
    var isSelected: Boolean = false
) : Parcelable
