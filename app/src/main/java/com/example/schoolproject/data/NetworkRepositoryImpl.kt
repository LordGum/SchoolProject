package com.example.schoolproject.data

import android.content.Context
import com.example.schoolproject.data.database.TodoItemDbModel
import com.example.schoolproject.data.mappers.MapperDto
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.data.utils.isValid
import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class NetworkRepositoryImpl(
    context: Context,
    private val preferences: TokenPreferences
): NetworkRepository {

    private val mapper = MapperDto()
    private val apiService = ApiFactory.apiService
    private val repositoryDb = TodoItemsRepositoryImpl(context)
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val token
        get() = preferences.getToken()

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn = (currentToken != null) && currentToken.isValid()
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override suspend fun checkAuthState() { checkAuthStateEvents.emit(Unit) }

    override suspend fun getTodoList(): List<TodoItemDbModel> {
        val response = apiService.loadTodoItemList()
        val newList = mapper.mapResponseDtoTodoListDb(response)
        repositoryDb.updateDatabase(newList)
        return newList
    }

    override suspend fun getTodoItem(id: String): TodoItem {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoItem(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }
}