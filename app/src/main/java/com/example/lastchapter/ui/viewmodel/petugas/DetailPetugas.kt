package com.example.lastchapter.ui.viewmodel.petugas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastchapter.model.Petugas
import com.example.lastchapter.repository.PetugasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailoffUiState {
    object Loading : DetailoffUiState()
    data class Success(val petugas: Petugas) : DetailoffUiState()
    object Error : DetailoffUiState()
}

class DetailPetugasViewModel(private val repository: PetugasRepository) : ViewModel() {
    private val _offuiState = MutableStateFlow<DetailoffUiState>(DetailoffUiState.Loading)
    val offuiState: StateFlow<DetailoffUiState> = _offuiState

    fun getPetugasById(idPetugas: String) {
        viewModelScope.launch {
            _offuiState.value = DetailoffUiState.Loading
            try {
                val petugas = repository.getPetugasById(idPetugas)
                _offuiState.value = DetailoffUiState.Success(petugas)
            } catch (e: IOException) {
                e.printStackTrace()
                _offuiState.value = DetailoffUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _offuiState.value = DetailoffUiState.Error
            }
        }
    }
}