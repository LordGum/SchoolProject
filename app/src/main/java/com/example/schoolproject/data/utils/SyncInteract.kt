package com.example.schoolproject.data.utils

import com.example.schoolproject.data.network.model.ElementDto
import com.example.schoolproject.data.utils.mappers.MapperDto
import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.TodoItemsRepository
import javax.inject.Inject

class SyncInteract @Inject constructor(
    private val localRepository: TodoItemsRepository,
    private val remoteRepository: NetworkRepository
) {
    private val mapperDto = MapperDto()

    suspend fun syncTasks() {
        val localList = localRepository.getTodoList().toMutableList()
        val localSet = localList.map { it.id }.toHashSet()
        val remoteList = remoteRepository.todoList.value

        val newElements = remoteList.filter { !localSet.contains(it.id) }
        localList.addAll(newElements.map { mapperDto.mapElementToEntity(it) })

        if(localList.isNotEmpty()) remoteRepository.refreshTodoItemList(localList)
        var totalList = remoteRepository.todoList.value
        if (totalList.isEmpty()) totalList = localList.map { mapperDto.mapEntityToElement(it) }
        syncLocalWithRemote(totalList)
    }

    private suspend fun syncLocalWithRemote(mergeList: List<ElementDto>) {
        localRepository.deleteTodoList()
        localRepository.addTodoList(mergeList.map { mapperDto.mapElementToEntity(it) })
    }
}
