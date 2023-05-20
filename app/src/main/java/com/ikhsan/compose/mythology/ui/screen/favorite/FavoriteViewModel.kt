package com.ikhsan.compose.mythology.ui.screen.favorite

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
class FavoriteViewModel @Inject constructor(
    private val repository: MythologyRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Mythology>>> = MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getFavoriteMythology() = viewModelScope.launch {
        repository.getFavoriteMythology()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }


    fun updateMythology(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateMythology(id, newState)
        getFavoriteMythology()
    }
}