package com.example.lastchapter.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.model.Hewan
import com.example.lastchapter.repository.HewanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomebeastUiState {
    data class Success(val hewan: List<Hewan>) : HomebeastUiState()
    object Error : HomebeastUiState()
    object Loading : HomebeastUiState()
}

class HomeHewanViewModel(private val beast: HewanRepository) : ViewModel() {

    var beastUIState: HomebeastUiState by mutableStateOf(HomebeastUiState.Loading)
        private set

    init {
        getHewan()
    }

    fun getHewan() {
        viewModelScope.launch {
            beastUIState = HomebeastUiState.Loading
            beastUIState = try {
                HomebeastUiState.Success(beast.getHewan())
            } catch (e: IOException) {
                HomebeastUiState.Error
            } catch (e: HttpException) {
                HomebeastUiState.Error
            }
        }
    }

    fun deleteHewan(idHewan: String) {
        viewModelScope.launch {
            try {
                beast.deleteHewan(idHewan)
            } catch (e: IOException) {
                beastUIState = HomebeastUiState.Error
            } catch (e: HttpException) {
                beastUIState = HomebeastUiState.Error
            }
        }
    }
}