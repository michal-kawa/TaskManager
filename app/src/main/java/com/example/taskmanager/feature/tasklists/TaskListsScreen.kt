package com.example.taskmanager.feature.tasklists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.R
import com.example.taskmanager.TaskNavigationBar
import com.example.taskmanager.core.common.component.TaskList
import com.example.taskmanager.core.common.component.TaskManagerFloatingActionButton
import com.example.taskmanager.core.common.component.TaskManagerTopBar
import com.example.taskmanager.navigation.BottomBarTab
import com.example.taskmanager.navigation.ListScreen
import com.example.taskmanager.navigation.SetupNavBottomGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListsScreen(
    navHostController: NavHostController,
    viewModel: TaskListsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val bottomNavItems = BottomBarTab.values().toList()
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val navBottomBarController = rememberNavController()

    LaunchedEffect(uiState) {
        viewModel.refreshTaskList()
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TaskManagerTopBar(
                navController = navHostController,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = stringResource(R.string.back_button_description),
                onNavigationClick = navHostController::popBackStack,
            )
        },
        bottomBar = { TaskNavigationBar(navBottomBarController, backStackEntry, bottomNavItems) },
        floatingActionButton = {
            TaskManagerFloatingActionButton(navHostController)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            SetupNavBottomGraph(navHostController, navBottomBarController, ListScreen.Todo.route, viewModel)
        }
    }
}

@Composable
fun TodoListScreen(
    navHostController: NavHostController,
    viewModel: TaskListsViewModel
) {
    TaskList(
        viewModel.getTodoTaskList(),
        navHostController,
        viewModel::updateTaskStatus,
        viewModel::removeTask
    )
}

@Composable
fun InProgressListScreen(
    navHostController: NavHostController,
    viewModel: TaskListsViewModel
) {
    TaskList(
        viewModel.getInprogressTaskList(),
        navHostController,
        viewModel::updateTaskStatus,
        viewModel::removeTask
    )
}


@Composable
fun DoneListScreen(
    navHostController: NavHostController,
    viewModel: TaskListsViewModel
) {
    TaskList(
        viewModel.getDoneTaskList(),
        navHostController,
        viewModel::updateTaskStatus,
        viewModel::removeTask
    )
}
