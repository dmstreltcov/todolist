package ru.streltsov.todolist.usecases

import ru.streltsov.todolist.data.provides.impl.UserProvider
import ru.streltsov.todolist.ui.base.Callback
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val provider: UserProvider) {
    operator fun invoke(email: String, password: String, callback: Callback) {
        provider.signUpByEmail(email, password, callback)
    }
}