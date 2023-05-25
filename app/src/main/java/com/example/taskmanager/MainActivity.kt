package com.example.taskmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.navigation.BottomBarTab
import com.example.taskmanager.navigation.Screen
import com.example.taskmanager.navigation.SetupNavGraph
import com.example.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
        Timber.plant(Timber.DebugTree())
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val bottomNavItems = BottomBarTab.values().toList()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val floatingButtonState = rememberSaveable { (mutableStateOf(true)) }

    when (backStackEntry.value?.destination?.route) {
        Screen.Todo.route, Screen.Inprogress.route, Screen.Done.route -> {
            bottomBarState.value = true
            floatingButtonState.value = true
        }

        else -> {
            bottomBarState.value = false
            floatingButtonState.value = false
        }
    }
    TaskManagerTheme {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TaskManagerTopBar(navController, bottomBarState)
            },
            bottomBar = {
                if (bottomBarState.value) {
                    TaskNavigationBar(navController, backStackEntry, bottomNavItems)
                }

            },
            floatingActionButton = {
                if (floatingButtonState.value) {
                    TaskManagerFloatingActionButton(navController)
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                SetupNavGraph(navController, Screen.Todo.route)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerTopBar(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    TopAppBar(
        title = {
            if (navController.currentBackStackEntry?.destination?.route == Screen.Add.route) {
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
            if (!bottomBarState.value) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, stringResource(R.string.back_button_description))
                }
            }
        }
    )
}

@Composable
fun TaskNavigationBar(
    navController: NavHostController,
    backStackEntry: State<NavBackStackEntry?>,
    bottomNavItems: List<BottomBarTab>
) {
    NavigationBar {
        bottomNavItems.forEach { destination ->
            val selected =
                destination.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(destination.route) },
                label = { Text(text = stringResource(id = destination.title)) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(destination.icon),
                        contentDescription = stringResource(
                            R.string.navigationBarItem_icon_description,
                            destination.name
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun TaskManagerFloatingActionButton(navController: NavHostController) {
    FloatingActionButton(onClick = { navController.navigate(Screen.Add.route) }) {
        Icon(Icons.Default.Add, stringResource(R.string.add_new_task_floating_button_description))
    }
}
