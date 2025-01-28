package com.example.lastchapter.ui.viewmodel.hewan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastchapter.model.Hewan
import com.example.lastchapter.repository.HewanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailbeastUiState {
    object Loading : DetailbeastUiState()
    data class Success(val hewan: Hewan) : DetailbeastUiState()
    object Error : DetailbeastUiState()
}

class DetailHewanViewModel(private val repository: HewanRepository) : ViewModel() {
    private val _beastuiState = MutableStateFlow<DetailbeastUiState>(DetailbeastUiState.Loading)
    val beastuiState: StateFlow<DetailbeastUiState> = _beastuiState

    fun getHewanById(idHewan: String) {
        viewModelScope.launch {
            _beastuiState.value = DetailbeastUiState.Loading
            try {
                val hewan = repository.getHewanById(idHewan)
                _beastuiState.value = DetailbeastUiState.Success(hewan)
            } catch (e: IOException) {
                e.printStackTrace()
                _beastuiState.value = DetailbeastUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _beastuiState.value = DetailbeastUiState.Error
            }
        }
    }
}
