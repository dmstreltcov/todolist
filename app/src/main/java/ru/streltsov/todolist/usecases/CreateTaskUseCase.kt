package ru.streltsov.todolist.usecases

import ru.streltsov.todolist.data.provides.impl.TaskProvider
import ru.streltsov.todolist.ui.base.Callback
import ru.streltsov.todolist.ui.tasklist.Task
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(private val provider: TaskProvider) {
    operator fun invoke(task:Task, callback:Callback){
        provider.addTask(task, callback)
    }
}