package com.example.taskmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.navigation.BottomBarTab
import com.example.taskmanager.navigation.MainScreen
import com.example.taskmanager.navigation.SetupNavHostGraph
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
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    TaskManagerTheme {
        SetupNavHostGraph(navController, MainScreen.TaskList.route)
    }
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
