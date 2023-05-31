package com.example.taskmanager.core.common.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.taskmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    value: String,
    labelResource: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    isError: Boolean = false,
    supportingText: Int = R.string.empty_string,
    singleLine: Boolean = true,
    trailingIcon: ImageVector? = null,
    trailingIconOnClick: (() -> Unit) = { }
) {
    OutlinedTextField(
        value = value,
        label = { Text(stringResource(labelResource)) },
        onValueChange = { onValueChange(it) },
        singleLine = singleLine,
        modifier = modifier,
        isError = isError,
        supportingText = {
            Text(stringResource(supportingText))
        },
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(onClick = { trailingIconOnClick() }) {
                    Icon(
                        trailingIcon,
                        contentDescription = stringResource(id = R.string.addTask_datePicker_icon_description)
                    )
                }
            }
        }
    )
}