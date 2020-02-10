package ru.streltsov.todolist.usecases

import ru.streltsov.todolist.data.provides.impl.TaskProvider
import ru.streltsov.todolist.ui.base.Callback
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val provider: TaskProvider) {
    operator fun invoke(id: String, callback:Callback.TaskCallback) {
        provider.getTaskById(id, callback)
    }
}