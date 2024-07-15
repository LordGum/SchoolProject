package com.example.schoolproject.data

import com.example.schoolproject.data.network.ApiService
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.data.network.model.ElementDto
import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.data.network.model.ReturnElementDto
import com.example.schoolproject.data.network.model.ReturnElementListDto
import com.example.schoolproject.data.utils.InternetConnectionManager
import com.example.schoolproject.data.utils.isValid
import com.example.schoolproject.data.utils.mappers.MapperDto
import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.entities.ErrorState
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val mapper: MapperDto,
    private val preferences: TokenPreferences,
    private val apiService: ApiService,
    private val connectionManager: InternetConnectionManager
) : NetworkRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _todoList = MutableStateFlow<List<ElementDto>>(emptyList())
    override val todoList = _todoList.asStateFlow()

    private val _revision = MutableStateFlow(0)

    private val _errorState: MutableStateFlow<ErrorState?> = MutableStateFlow(null)
    override val errorState: StateFlow<ErrorState?>
        get() = _errorState.asStateFlow()

    private fun checkInternet(): Boolean {
        if (connectionManager.internetState.value) return true
        else {
            _errorState.update { ErrorState.NoInternetError }
            return false
        }
    }

    private suspend fun handle(block: suspend () -> Response<ResponseListDto>): ResponseListDto {
        val response = block.invoke()
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            val typeError = when (response.code()) {
                400 -> ErrorState.RequestError
                401 -> ErrorState.NoAuthError
                404 -> ErrorState.NotFoundError
                500 -> ErrorState.ServerError
                else -> ErrorState.UnknownError
            }
            _errorState.update { typeError }
        } else return body
        throw RuntimeException("error in handle")
    }

    init {
        coroutineScope.launch { getTodoList() }
        connectionManager.refreshOneTime()
        connectionManager.refreshIn8hours()
    }

    private val token
        get() = preferences.getToken()

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn =
                (currentToken != null) && (currentToken.value.isNotBlank()) && currentToken.isValid()
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }

    override fun getAuthStateFlow(): Flow<AuthState> = authStateFlow

    override suspend fun checkAuthState() { checkAuthStateEvents.emit(Unit) }

    override suspend fun getTodoList() {
        if (checkInternet()) {
            val response = handle { apiService.loadTodoItemList() }
            _todoList.update { response.list }
            _revision.update { response.revision }
        }
    }

    override suspend fun deleteTodoItem(id: String) {
        if (checkInternet()) {
            val response = handle {
                apiService.deleteTodoItem(id, _revision.value)
            }
            _revision.update { response.revision }
        }
    }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        if (checkInternet()) {
            val response = handle {
                val element = ReturnElementDto(mapper.mapEntityToElement(todoItem))
                apiService.addTodoItem(_revision.value, element)
            }
            _revision.update { response.revision }
        }
    }

    override suspend fun refactorTodoItem(todoItem: TodoItem) {
        if (checkInternet()) {
            val response = handle {
                val element = ReturnElementDto(mapper.mapEntityToElement(todoItem))
                apiService.refactorTodoItem(todoItem.id, _revision.value, element)
            }
            _revision.update { response.revision }
        }
    }

    override suspend fun refreshTodoItemList(list: List<TodoItem>) {
        if (checkInternet()) {
            val response = handle {
                val returnList = ReturnElementListDto(list.map { mapper.mapEntityToElement(it) })
                apiService.updateTodoItemListOnService(_revision.value, returnList)
            }
            _todoList.update { response.list }
            _revision.update { response.revision }
        }
    }
}