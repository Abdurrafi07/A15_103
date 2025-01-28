package com.example.lastchapter.ui.viewmodel.monitoring

import com.example.lastchapter.model.Monitoring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.MonitoringRepository
import kotlinx.coroutines.launch

class InsertMonitoringViewModel(private val monitoringRepository: MonitoringRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertmonUiState())
        private set

    fun updateInsertMonitoringState(insertmonUiEvent: InsertmonUiEvent) {
        uiState = InsertmonUiState(insertmonUiEvent = insertmonUiEvent)
    }

    suspend fun insertMonitoring() {
        viewModelScope.launch {
            try {
                monitoringRepository.insertMonitoring(uiState.insertmonUiEvent.toMonitoring())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertmonUiState(
    val insertmonUiEvent: InsertmonUiEvent = InsertmonUiEvent()
)

data class InsertmonUiEvent(
    val idMonitoring: String = "",
    val tanggalMonitoring: String = "",
    val hewanSakit: String = "",
    val hewanSehat: String = "",
    val status: String = ""
)

fun InsertmonUiEvent.toMonitoring(): Monitoring = Monitoring(
    idMonitoring = idMonitoring,
    tanggalMonitoring = tanggalMonitoring,
    hewanSakit = hewanSakit,
    hewanSehat = hewanSehat,
    status = status
)

fun Monitoring.toUiStateMonitoring(): InsertmonUiState = InsertmonUiState(
    insertmonUiEvent = toInsertmonUiEvent()
)

fun Monitoring.toInsertmonUiEvent(): InsertmonUiEvent = InsertmonUiEvent(
    idMonitoring = idMonitoring,
    tanggalMonitoring = tanggalMonitoring,
    hewanSakit = hewanSakit,
    hewanSehat = hewanSehat,
    status = status
)
