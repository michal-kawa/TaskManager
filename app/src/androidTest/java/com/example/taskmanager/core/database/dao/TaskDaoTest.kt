package com.example.taskmanager.core.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.taskmanager.core.data.model.TaskStatus
import com.example.taskmanager.core.database.TaskManagerDatabase
import com.example.taskmanager.core.database.model.TaskEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDaoTest {

    private lateinit var database: TaskManagerDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TaskManagerDatabase::class.java
        ).allowMainThreadQueries().build()

        taskDao = database.taskDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertAndGetNewTask() = runTest {
        // Given
        val task = createSingleTaskEntity()

        // When
        database.taskDao().insertTask(task)
        val resultTask = taskDao.getTaskById(task.id).first()
        val resultTasks = taskDao.getTaskList("TODO").first()


        // Then
        assertEquals(task, resultTask)
        assertEquals(task, resultTasks.first())
    }

    @Test
    fun removeTask() = runTest {
        // Given
        var task = createSingleTaskEntity()

        // When
        database.taskDao().insertTask(task)
        task = database.taskDao().getTaskList(TaskStatus.TODO.name).first().first()
        database.taskDao().removeTask(task)
        val resultTasks = taskDao.getTaskList("TODO").first()

        // Then
        assertEquals(0, resultTasks.size)
    }

    @Test
    fun getTodoTasksList() = runTest {
        // Given
        val todoTasks = createTodoTaskList()

        // When
        todoTasks.map { database.taskDao().insertTask(it) }
        val resultTasks = taskDao.getTaskList(TaskStatus.TODO.name)

        // Then
        assertEquals(3, resultTasks.first().size)
        assertEquals("First todo task title", resultTasks.first().first().title)
    }

    @Test
    fun getInProgressTasksList() = runTest {
        // Given
        val todoTasks = createInProgressTaskList()

        // When
        todoTasks.map { database.taskDao().insertTask(it) }
        val resultTasks = taskDao.getTaskList(TaskStatus.IN_PROGRESS.name)

        // Then
        assertEquals(3, resultTasks.first().size)
        assertEquals("First in progress task title", resultTasks.first().first().title)
    }

    @Test
    fun getDoneTasksList() = runTest {
        // Given
        val todoTasks = createDoneTaskList()

        // When
        todoTasks.map { database.taskDao().insertTask(it) }
        val resultTasks = taskDao.getTaskList(TaskStatus.DONE.name)

        // Then
        assertEquals(3, resultTasks.first().size)
        assertEquals("First done task title", resultTasks.first().first().title)
    }


    private fun createSingleTaskEntity(): TaskEntity = TaskEntity(
        id = 1,
        title = "Task title",
        description = "Task description",
        date = "11.05.2023",
        taskStatus = TaskStatus.TODO.name
    )

    private fun createTodoTaskList(): List<TaskEntity> = listOf(
        TaskEntity(
            id = 2,
            title = "First todo task title",
            description = "First todo task description",
            date = "11.05.2023",
            taskStatus = TaskStatus.TODO.name
        ),
        TaskEntity(
            id = 3,
            title = "Second todo task title",
            description = "Second todo task description",
            date = "12.08.2023",
            taskStatus = TaskStatus.TODO.name
        ),
        TaskEntity(
            id = 4,
            title = "Third todo task title",
            description = "Third todo task description",
            date = "1.10.2023",
            taskStatus = TaskStatus.TODO.name
        )
    )

    private fun createInProgressTaskList(): List<TaskEntity> = listOf(
        TaskEntity(
            id = 5,
            title = "First in progress task title",
            description = "First in progress task description",
            date = "5.02.2023",
            taskStatus = TaskStatus.IN_PROGRESS.name
        ),
        TaskEntity(
            id = 6,
            title = "Second in progress task title",
            description = "Second in progress task description",
            date = "25.04.2023",
            taskStatus = TaskStatus.IN_PROGRESS.name
        ),
        TaskEntity(
            id = 7,
            title = "Third in progress task title",
            description = "Third in progress task description",
            date = "9.06.2023",
            taskStatus = TaskStatus.IN_PROGRESS.name
        )
    )

    private fun createDoneTaskList(): List<TaskEntity> = listOf(
        TaskEntity(
            id = 8,
            title = "First done task title",
            description = "First done task description",
            date = "16.03.2023",
            taskStatus = TaskStatus.DONE.name
        ),
        TaskEntity(
            id = 9,
            title = "Second done task title",
            description = "Second done task description",
            date = "8.05.2023",
            taskStatus = TaskStatus.DONE.name
        ),
        TaskEntity(
            id = 10,
            title = "Third done task title",
            description = "Third done task description",
            date = "30.07.2023",
            taskStatus = TaskStatus.DONE.name
        )
    )
}