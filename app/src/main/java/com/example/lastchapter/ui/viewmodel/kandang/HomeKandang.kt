package com.example.lastchapter.ui.viewmodel.kandang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.model.Kandang
import com.example.lastchapter.repository.KandangRepository
import com.example.lastchapter.ui.viewmodel.petugas.HomeoffUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomecageUiState {
    data class Success(val kandang: List<Kandang>) : HomecageUiState()
    object Error : HomecageUiState()
    object Loading : HomecageUiState()
}

class HomeKandangViewModel(private val cage: KandangRepository) : ViewModel() {

    var cageUIState: HomecageUiState by mutableStateOf(HomecageUiState.Loading)
        private set

    var searchQuery: String by mutableStateOf("")
        private set

    init {
        getKandang()
    }

    fun getKandang() {
        viewModelScope.launch {
            cageUIState = HomecageUiState.Loading
            cageUIState = try {
                HomecageUiState.Success(cage.getKandang())
            } catch (e: IOException) {
                HomecageUiState.Error
            } catch (e: HttpException) {
                HomecageUiState.Error
            }
        }
    }

    fun deleteKandang(idKandang: String) {
        viewModelScope.launch {
            try {
                cage.deleteKandang(idKandang)
            } catch (e: IOException) {
                cageUIState = HomecageUiState.Error
            } catch (e: HttpException) {
                cageUIState = HomecageUiState.Error
            }
        }
    }

    fun searchKandang(query: String) {
        viewModelScope.launch {
            searchQuery = query
            cageUIState = HomecageUiState.Loading
            cageUIState = try {
                val allKandang = cage.getKandang()
                val filteredKandang = allKandang.filter {
                    it.lokasi.contains(query, ignoreCase = true) || it.idKandang.contains(query, ignoreCase = true)
                }
                HomecageUiState.Success(filteredKandang)
            } catch (e: IOException) {
                HomecageUiState.Error
            } catch (e: HttpException) {
                HomecageUiState.Error
            }
        }
    }
}
