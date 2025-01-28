package com.example.lastchapter.ui.viewmodel.petugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.model.Petugas
import com.example.lastchapter.repository.PetugasRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeoffUiState {
    data class Success(val petugas: List<Petugas>) : HomeoffUiState()
    object Error : HomeoffUiState()
    object Loading : HomeoffUiState()
}

class HomePetugasViewModel(private val off: PetugasRepository) : ViewModel() {

    var offUIState: HomeoffUiState by mutableStateOf(HomeoffUiState.Loading)
        private set

    init {
        getPetugas()
    }

    fun getPetugas() {
        viewModelScope.launch {
            offUIState = HomeoffUiState.Loading
            offUIState = try {
                HomeoffUiState.Success(off.getPetugas())
            } catch (e: IOException) {
                HomeoffUiState.Error
            } catch (e: HttpException) {
                HomeoffUiState.Error
            }
        }
    }

    fun deletePetugas(idPetugas: String) {
        viewModelScope.launch {
            try {
                off.deletePetugas(idPetugas)
            } catch (e: IOException) {
                offUIState = HomeoffUiState.Error
            } catch (e: HttpException) {
                offUIState = HomeoffUiState.Error
            }
        }
    }
}