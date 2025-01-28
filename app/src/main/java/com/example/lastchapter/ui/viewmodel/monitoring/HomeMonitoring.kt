package com.example.lastchapter.ui.viewmodel.monitoring

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.model.Monitoring
import com.example.lastchapter.repository.MonitoringRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomemonUiState {
    data class Success(val monitoring: List<Monitoring>) : HomemonUiState()
    object Error : HomemonUiState()
    object Loading : HomemonUiState()
}

class HomeMonitoringViewModel(private val mon: MonitoringRepository) : ViewModel() {

    var monUIState: HomemonUiState by mutableStateOf(HomemonUiState.Loading)
        private set

    init {
        getMonitoring()
    }

    fun getMonitoring() {
        viewModelScope.launch {
            monUIState = HomemonUiState.Loading
            monUIState = try {
                HomemonUiState.Success(mon.getMonitoring())
            } catch (e: IOException) {
                HomemonUiState.Error
            } catch (e: HttpException) {
                HomemonUiState.Error
            }
        }
    }

    fun deleteMonitoring(idMonitoring: String) {
        viewModelScope.launch {
            try {
                mon.deleteMonitoring(idMonitoring)
            } catch (e: IOException) {
                monUIState = HomemonUiState.Error
            } catch (e: HttpException) {
                monUIState = HomemonUiState.Error
            }
        }
    }
}
