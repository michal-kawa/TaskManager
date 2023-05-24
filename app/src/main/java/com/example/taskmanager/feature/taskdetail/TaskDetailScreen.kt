package com.example.taskmanager.feature.taskdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import timber.log.Timber

@Composable
fun TaskDetailScreen(viewModel: TaskDetailViewModel = hiltViewModel()) {

    val state = viewModel.uiState.collectAsState()

    Column(Modifier.padding(dimensionResource(id = R.dimen.padding_normal))) {
        Timber.d("Ladowanie widoku")
        state.value.task?.let {
            Text(
                text = it.title
            )
        }
    }

}
