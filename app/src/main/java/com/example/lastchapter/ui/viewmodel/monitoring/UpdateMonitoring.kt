package com.example.lastchapter.ui.viewmodel.monitoring

import com.example.lastchapter.model.Monitoring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.MonitoringRepository
import kotlinx.coroutines.launch

class UpdateMonitoringViewModel(private val monitoringRepository: MonitoringRepository) : ViewModel() {
    var monUiState by mutableStateOf(UpdatemonUiState())
        private set

    fun loadMonitoring(idMonitoring: String) {
        viewModelScope.launch {
            try {
                val monitoring = monitoringRepository.getMonitoringById(idMonitoring)
                monUiState = UpdatemonUiState(updatemonUiEvent = monitoring.toUpdatemonUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateMonitoringState(updatemonUiEvent: UpdatemonUiEvent) {
        monUiState = UpdatemonUiState(updatemonUiEvent = updatemonUiEvent)
    }

    suspend fun updateMonitoring(idMonitoring: String) {
        viewModelScope.launch {
            try {
                monitoringRepository.updateMonitoring(idMonitoring, monUiState.updatemonUiEvent.toMonitoring())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatemonUiState(
    val updatemonUiEvent: UpdatemonUiEvent = UpdatemonUiEvent()
)

data class UpdatemonUiEvent(
    val idMonitoring: String = "",
    val tanggalMonitoring: String = "",
    val hewanSakit: String = "",
    val hewanSehat: String = "",
    val status: String = ""
)

fun UpdatemonUiEvent.toMonitoring(): Monitoring = Monitoring(
    idMonitoring = idMonitoring,
    tanggalMonitoring = tanggalMonitoring,
    hewanSakit = hewanSakit,
    hewanSehat = hewanSehat,
    status = status
)

fun Monitoring.toUpdatemonUiEvent(): UpdatemonUiEvent = UpdatemonUiEvent(
    idMonitoring = idMonitoring,
    tanggalMonitoring = tanggalMonitoring,
    hewanSakit = hewanSakit,
    hewanSehat = hewanSehat,
    status = status
)
