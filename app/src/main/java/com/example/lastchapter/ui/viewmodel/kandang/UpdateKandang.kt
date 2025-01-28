package com.example.lastchapter.ui.viewmodel.kandang

import com.example.lastchapter.model.Kandang
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.KandangRepository
import kotlinx.coroutines.launch

class UpdateKandangViewModel(private val kandangRepository: KandangRepository) : ViewModel() {
    var cageUiState by mutableStateOf(UpdatecageUiState())
        private set

    fun loadKandang(idKandang: String) {
        viewModelScope.launch {
            try {
                val kandang = kandangRepository.getKandangById(idKandang)
                cageUiState = UpdatecageUiState(updatecageUiEvent = kandang.toUpdatecageUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateKandangState(updatecageUiEvent: UpdatecageUiEvent) {
        cageUiState = UpdatecageUiState(updatecageUiEvent = updatecageUiEvent)
    }

    suspend fun updateKandang(idKandang: String) {
        viewModelScope.launch {
            try {
                kandangRepository.updateKandang(idKandang, cageUiState.updatecageUiEvent.toKandang())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatecageUiState(
    val updatecageUiEvent: UpdatecageUiEvent = UpdatecageUiEvent()
)

data class UpdatecageUiEvent(
    val idKandang: String = "",
    val kapasitas: String = "",
    val lokasi: String = ""
)

fun UpdatecageUiEvent.toKandang(): Kandang = Kandang(
    idKandang = idKandang,
    kapasitas = kapasitas,
    lokasi = lokasi
)

fun Kandang.toUpdatecageUiEvent(): UpdatecageUiEvent = UpdatecageUiEvent(
    idKandang = idKandang,
    kapasitas = kapasitas,
    lokasi = lokasi
)
