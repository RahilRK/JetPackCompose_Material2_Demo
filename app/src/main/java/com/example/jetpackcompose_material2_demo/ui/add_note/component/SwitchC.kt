package com.example.jetpackcompose_material2_demo.ui.add_note.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcompose_material2_demo.ui.add_note.AddNoteViewModel

@Preview
@Composable
fun SwitchC(onCheckedEvent: (isChecked: Boolean) -> Unit = {},
            checkedValue: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {

//        val viewModel: AddNoteViewModel = hiltViewModel()
//        val getCheckedValue by viewModel.switch_isChecked.collectAsState()

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Mark as important",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            modifier = Modifier.padding(end = 8.dp),
//            checked = getCheckedValue,
            checked = checkedValue,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primary,
            ),
            onCheckedChange = {

//                viewModel.switchEvent(it)
                onCheckedEvent(it)
            },
        )
    }
}