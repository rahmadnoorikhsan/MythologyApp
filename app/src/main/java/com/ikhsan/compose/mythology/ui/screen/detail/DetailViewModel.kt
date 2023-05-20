package com.ikhsan.compose.mythology.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhsan.compose.mythology.data.MythologyRepository
import com.ikhsan.compose.mythology.model.Mythology
import com.ikhsan.compose.mythology.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MythologyRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Mythology>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Mythology>> get() = _uiState

    fun getMythologyById(id: Int) = viewModelScope.launch {
        repository.getMythologyById(id)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}