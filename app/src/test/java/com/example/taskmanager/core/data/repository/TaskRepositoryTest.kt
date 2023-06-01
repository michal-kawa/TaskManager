package com.example.taskmanager.core.data.repository

import com.example.taskmanager.core.data.model.Task
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.core.data.testdao.TestTaskDao
import com.example.taskmanager.core.database.dao.TaskDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var taskDao: TaskDao

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setup() {
        taskDao = TestTaskDao()
        taskRepository = ImplTaskRepository(taskDao)
    }

    @Test
    fun insertAndGetNewTask() = testScope.runTest {
        // Given
        val task =
            Task(
                0,
                "Test task title",
                "Test task description",
                "19.05.2023",
                TaskStatus.TODO
            )

        // When
        taskRepository.addTask(task)

        // Then
        val resultTask = taskRepository.getTaskById(0).first()
        assertEquals(task, resultTask)
    }
}