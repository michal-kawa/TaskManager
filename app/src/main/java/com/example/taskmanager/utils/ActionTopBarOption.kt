package com.example.taskmanager.utils

import androidx.compose.ui.graphics.vector.ImageVector

data class ActionTopBarOption(
    var showActionIcon: Boolean,
    var actionIcon: ImageVector,
    var actionIconContentDescription: String,
    var onActionClick: () -> Unit,
)