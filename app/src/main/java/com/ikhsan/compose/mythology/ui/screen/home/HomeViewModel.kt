package com.ikhsan.compose.mythology.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhsan.compose.mythology.data.MythologyRepository
import com.ikhsan.compose.mythology.model.Mythology
import com.ikhsan.compose.mythology.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MythologyRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Mythology>>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _active = mutableStateOf(false)
    val active: State<Boolean> get() = _active

    private val _history = mutableStateListOf<String>()
    val history get() = _history

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchMythology(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun saveHistory(query: String, save: Boolean) {
        if (query.isNotEmpty() && save) {
            _history.add(query)
        }
    }

    fun active(newActive: Boolean) = viewModelScope.launch {
        _active.value = newActive
    }

    fun updateMythology(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateMythology(id, newState)
            .collect {isUpdate ->
                if (isUpdate) search(_query.value)
            }
    }
}