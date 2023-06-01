package com.example.taskmanager.core.common.component

import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.taskmanager.R
import com.example.taskmanager.navigation.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerTopBar(
    navController: NavHostController,
    navigationIcon: ImageVector?,
    navigationIconContentDescription: String?,
    onNavigationClick: () -> Unit = { },
    showActionIcon: Boolean = false,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    onActionClick: () -> Unit = { },

    ) {
    TopAppBar(
        title = {
            if (navController.currentBackStackEntry?.destination?.route == MainScreen.Add.route) {
                Text(
                    stringResource(R.string.add_new_task_topBar_title)
                )
            } else {
                Text(
                    stringResource(R.string.lists_topBar_title)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = { onNavigationClick() }) {
                    Icon(navigationIcon, navigationIconContentDescription)
                }
            }
        },
        actions = {
            if (showActionIcon) {
                IconButton(onClick = { onActionClick() }) {
                    Icon(actionIcon!!, actionIconContentDescription)
                }
            }
        }
    )
}