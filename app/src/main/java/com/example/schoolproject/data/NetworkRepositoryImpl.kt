package com.example.schoolproject.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.WorkManager
import com.example.schoolproject.data.network.ApiFactory
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import retrofit2.Response

class NetworkRepositoryImpl(
    context: Context
) : NetworkRepository {

    private val mapper = MapperDto()
    private val preferences = TokenPreferences(context)
    private val apiService = ApiFactory.apiService
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _errorState: MutableStateFlow<ErrorState?> = MutableStateFlow(null)
    override val errorState: StateFlow<ErrorState?>
        get() = _errorState.asStateFlow()

    private suspend fun handle(block: suspend () -> Response<ResponseListDto>): ResponseListDto? {
        if (!connectionManager.internetState.value) {
            Log.d("tag", "internet nonononono")
            _errorState.update { ErrorState.NoInternetError }
        }
        else {
            Log.d("tag", "internet yyyyy")
            val response = block.invoke()
            val body = response.body()
            if (!response.isSuccessful || body == null) {
                Log.d("tag", "error")
                val typeError = when (response.code()) {
                    400 -> ErrorState.RequestError
                    401 -> ErrorState.NoAuthError
                    404 -> ErrorState.NotFoundError
                    500 -> ErrorState.ServerError
                    else -> ErrorState.UnknownError
                }
                _errorState.update { typeError }
            }
            return body
        }
        return null
    }

    val connectionManager by lazy {
        InternetConnectionManager(
            connectivityManager = getSystemService(
                context,
                ConnectivityManager::class.java
            ) as ConnectivityManager,
            workManager = WorkManager.getInstance(context)
        )
    }

    init {
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
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun getTodoList(): Pair<List<ElementDto>, Int> {
        val response = handle { apiService.loadTodoItemList() }
        val revision = response?.revision ?: throw RuntimeException("revision is null")
        return response.list to revision
    }

    override suspend fun deleteTodoItem(id: String) {
        handle {
            val revision = getTodoList().second
            apiService.deleteTodoItem(id, revision)
        }
    }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        handle {
            val revision = getTodoList().second
            val element = ReturnElementDto(mapper.mapEntityToElement(todoItem))
            apiService.addTodoItem(revision, element)
        }
    }

    override suspend fun refactorTodoItem(todoItem: TodoItem) {
        handle {
            val revision = getTodoList().second
            val element = ReturnElementDto(mapper.mapEntityToElement(todoItem))
            apiService.refactorTodoItem(todoItem.id, revision, element)
        }
    }

    override suspend fun refreshTodoItemList(list: List<TodoItem>): List<ElementDto> {
        val response = handle {
            val revision = getTodoList().second
            val returnList = ReturnElementListDto(list.map { mapper.mapEntityToElement(it) })
            apiService.updateTodoItemListOnService(revision, returnList)
        }
        return response?.list ?: throw RuntimeException("list is null")
    }
}