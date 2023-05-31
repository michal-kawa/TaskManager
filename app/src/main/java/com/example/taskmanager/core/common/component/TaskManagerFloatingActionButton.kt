package com.example.taskmanager.core.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.taskmanager.R
import com.example.taskmanager.navigation.MainScreen

@Composable
fun TaskManagerFloatingActionButton(navController: NavHostController) {
    FloatingActionButton(onClick = { navController.navigate(MainScreen.Add.route) }) {
        Icon(Icons.Default.Add, stringResource(R.string.add_new_task_floating_button_description))
    }
}