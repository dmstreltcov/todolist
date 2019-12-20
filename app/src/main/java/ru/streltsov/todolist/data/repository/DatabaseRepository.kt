package ru.streltsov.todolist.data.repository

interface DatabaseRepository : UserRepository, TaskListRepository, TaskRepository {
}