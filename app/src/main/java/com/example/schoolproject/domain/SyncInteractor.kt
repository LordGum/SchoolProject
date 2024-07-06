package com.example.schoolproject.domain

import com.example.schoolproject.data.mappers.MapperDto
import com.example.schoolproject.data.network.model.ElementDto

class SyncInteractor(
    private val localRepository: TodoItemsRepository,
    private val remoteRepository: NetworkRepository
) {
    private val mapper = MapperDto()
    suspend fun syncTasks() {
        val remoteTasks = remoteRepository.getTodoList().await().list
        syncLocalWithRemote(remoteTasks)
    }

    private suspend fun syncLocalWithRemote(remoteTasks: List<ElementDto>) {
        localRepository.deleteTodoList()
        localRepository.addTodoList(remoteTasks.map { mapper.mapElementToEntity(it) })
    }
}
