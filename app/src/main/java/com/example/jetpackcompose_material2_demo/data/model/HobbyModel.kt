package com.example.jetpackcompose_material2_demo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.net.IDN

@Parcelize
data class HobbyModel(
    var title: String = "",
    var isChecked: Boolean = false,
):Parcelable