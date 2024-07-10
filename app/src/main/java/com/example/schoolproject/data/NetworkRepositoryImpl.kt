package com.example.schoolproject.data

import android.content.Context
import android.util.Log
import com.example.schoolproject.data.utils.mappers.MapperDto
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.data.network.model.ReturnElementDto
import com.example.schoolproject.data.network.model.ReturnElementListDto
import com.example.schoolproject.data.utils.isValid
import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NetworkRepositoryImpl(
    context: Context
): NetworkRepository {

    private val mapper = MapperDto()
    private val preferences = TokenPreferences(context)
    private val apiService = ApiFactory.apiService
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }

    private val token
        get() = preferences.getToken()

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn = (currentToken != null) && (currentToken.value.isNotBlank()) && currentToken.isValid()
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

    override fun getTodoList(): Deferred<ResponseListDto> {
        return coroutineScope.async(exceptionHandler) {
            apiService.loadTodoItemList()
        }
    }

    override fun deleteTodoItem(id: String) {
        coroutineScope.launch(exceptionHandler) {
            val revision = getTodoList().await().revision
            apiService.deleteTodoItem(id, revision)
        }
    }

    override fun addTodoItem(todoItem: TodoItem) {
        coroutineScope.launch(exceptionHandler) {
            val revision = getTodoList().await().revision
            val element =  ReturnElementDto(mapper.mapEntityToElement(todoItem))
            apiService.addTodoItem(revision, element)
        }
    }

    override fun refactorTodoItem(todoItem: TodoItem) {
        coroutineScope.launch(exceptionHandler) {
            val revision = getTodoList().await().revision
            val element =  ReturnElementDto(mapper.mapEntityToElement(todoItem))
            apiService.refactorTodoItem(todoItem.id, revision, element)
        }
    }

    override fun refreshTodoItemList(list: List<TodoItem>): Deferred<ResponseListDto>  {
        return coroutineScope.async(exceptionHandler) {
            val revision = getTodoList().await().revision
            val returnList = ReturnElementListDto( list.map { mapper.mapEntityToElement(it) } )
            apiService.updateTodoItemListOnService(revision, returnList)
        }
    }
}