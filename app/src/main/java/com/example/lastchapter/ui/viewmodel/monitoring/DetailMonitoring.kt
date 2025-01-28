package com.example.lastchapter.ui.viewmodel.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.lastchapter.model.Monitoring
import com.example.lastchapter.repository.MonitoringRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailmonUiState {
    object Loading : DetailmonUiState()
    data class Success(val monitoring: Monitoring) : DetailmonUiState()
    object Error : DetailmonUiState()
}

class DetailMonitoringViewModel(private val repository: MonitoringRepository) : ViewModel() {
    private val _monUiState = MutableStateFlow<DetailmonUiState>(DetailmonUiState.Loading)
    val monUiState: StateFlow<DetailmonUiState> = _monUiState

    fun getMonitoringById(idMonitoring: String) {
        viewModelScope.launch {
            _monUiState.value = DetailmonUiState.Loading
            try {
                val monitoring = repository.getMonitoringById(idMonitoring)
                _monUiState.value = DetailmonUiState.Success(monitoring)
            } catch (e: IOException) {
                e.printStackTrace()
                _monUiState.value = DetailmonUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _monUiState.value = DetailmonUiState.Error
            }
        }
    }
}
