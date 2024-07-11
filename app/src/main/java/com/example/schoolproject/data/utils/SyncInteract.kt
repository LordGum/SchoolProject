package com.example.schoolproject.data.utils

import com.example.schoolproject.data.network.model.ElementDto
import com.example.schoolproject.data.utils.mappers.MapperDb
import com.example.schoolproject.data.utils.mappers.MapperDto
import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.TodoItemsRepository

class SyncInteract(
    private val localRepository: TodoItemsRepository,
    private val remoteRepository: NetworkRepository
) {
    private val mapperDto = MapperDto()
    private val mapperDb = MapperDb()

    suspend fun syncTasks() {
        val localList =
            localRepository.getTodoList().map { mapperDb.dbModelToEntity(it) }.toMutableList()
        val localSet = localList.map { it.id }.toHashSet()
        val remoteList = remoteRepository.getTodoList()

        val newElements = remoteList.first.filter { !localSet.contains(it.id) }
        localList.addAll(newElements.map { mapperDto.mapElementToEntity(it) })

        val mergeList = remoteRepository.refreshTodoItemList(localList)
        syncLocalWithRemote(mergeList)
    }

    private suspend fun syncLocalWithRemote(mergeList: List<ElementDto>) {
        localRepository.deleteTodoList()
        localRepository.addTodoList(mergeList.map { mapperDto.mapElementToEntity(it) })
    }
}
