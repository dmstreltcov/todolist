package ru.streltsov.todolist.usecases

import ru.streltsov.todolist.data.provides.impl.TaskListProvider
import javax.inject.Inject
import ru.streltsov.todolist.ui.base.Callback

class GetTaskListUseCase @Inject constructor(private val provider:TaskListProvider) {
    operator fun invoke(callback:Callback.TaskListCallback){
        provider.getAllTasks(callback)
    }
}