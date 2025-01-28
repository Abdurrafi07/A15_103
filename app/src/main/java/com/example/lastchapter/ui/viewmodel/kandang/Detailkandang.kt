package com.example.lastchapter.ui.viewmodel.kandang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastchapter.model.Kandang
import com.example.lastchapter.repository.KandangRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailcageUiState {
    object Loading : DetailcageUiState()
    data class Success(val kandang: Kandang) : DetailcageUiState()
    object Error : DetailcageUiState()
}

class DetailKandangViewModel(private val repository: KandangRepository) : ViewModel() {
    private val _cageuiState = MutableStateFlow<DetailcageUiState>(DetailcageUiState.Loading)
    val cageuiState: StateFlow<DetailcageUiState> = _cageuiState

    fun getKandangById(idKandang: String) {
        viewModelScope.launch {
            _cageuiState.value = DetailcageUiState.Loading
            try {
                val kandang = repository.getKandangById(idKandang)
                _cageuiState.value = DetailcageUiState.Success(kandang)
            } catch (e: IOException) {
                e.printStackTrace()
                _cageuiState.value = DetailcageUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _cageuiState.value = DetailcageUiState.Error
            }
        }
    }
}