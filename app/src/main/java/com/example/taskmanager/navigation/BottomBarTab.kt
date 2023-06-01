package com.example.taskmanager.navigation

import com.example.taskmanager.R

enum class BottomBarTab(
    val title: Int,
    val route: String,
    val icon: Int,
) {
    TODO(
        icon = R.drawable.todo_icon,
        title = R.string.to_do_navigation_title,
        route = ListScreen.Todo.route
    ),
    INPROGRESS(
        icon = R.drawable.inprogress_icon,
        title = R.string.in_progress_navigation_title,
        route = ListScreen.Inprogress.route
    ),
    DONE(
        icon = R.drawable.done_icon,
        title = R.string.done_navigation_title,
        route = ListScreen.Done.route
    )
}