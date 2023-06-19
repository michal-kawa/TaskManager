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
import com.example.taskmanager.utils.ActionTopBarOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerTopBar(
    topBarTitle: Int,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    onNavigationClick: () -> Unit = { },
    actionOptions: List<ActionTopBarOption> = emptyList(),

    ) {
    TopAppBar(
        title = { Text(stringResource(topBarTitle)) },
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
            for (action in actionOptions) {
                if (action.showActionIcon) {
                    IconButton(onClick = { action.onActionClick() }) {
                        Icon(action.actionIcon, action.actionIconContentDescription)
                    }
                }
            }
        }
    )
}