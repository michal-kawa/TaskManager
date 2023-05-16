package com.example.taskmanager.core.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.taskmanager.R
import com.example.taskmanager.core.data.model.Task

@Composable
fun TaskItem(task: Task) {
    val normalPadding = dimensionResource(id = R.dimen.padding_normal)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(normalPadding)
    ) {
        Text(task.title)
        Text(task.description)
        Text(task.createDate)
    }
}