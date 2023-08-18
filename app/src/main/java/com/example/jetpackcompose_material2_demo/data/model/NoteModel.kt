package com.example.jetpackcompose_material2_demo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String = "",
    var description: String = "",
    var hobbies: String = "",
    var isImp: Boolean = false,
    var color: String = "",
    var bgColor: String = "",
    var tag: String = "",
):Parcelable