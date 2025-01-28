package com.example.lastchapter.ui.viewmodel.hewan

import com.example.lastchapter.model.Hewan
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.HewanRepository
import kotlinx.coroutines.launch

class UpdateHewanViewModel(private val hewanRepository: HewanRepository) : ViewModel() {
    var beastUiState by mutableStateOf(UpdatebeastUiState())
        private set

    fun loadHewan(idHewan: String) {
        viewModelScope.launch {
            try {
                val hewan = hewanRepository.getHewanById(idHewan)
                beastUiState = UpdatebeastUiState(updatebeastUiEvent = hewan.toUpdatebeastUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateHewanState(updatebeastUiEvent: UpdatebeastUiEvent) {
        beastUiState = UpdatebeastUiState(updatebeastUiEvent = updatebeastUiEvent)
    }

    suspend fun updateHewan(idHewan: String) {
        viewModelScope.launch {
            try {
                hewanRepository.updateHewan(idHewan, beastUiState.updatebeastUiEvent.toHewan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatebeastUiState(
    val updatebeastUiEvent: UpdatebeastUiEvent = UpdatebeastUiEvent()
)

data class UpdatebeastUiEvent(
    val idHewan: String = "",
    val namaHewan: String = "",
    val tipePakan: String = "",
    val populasi: String = "",
    val zonaWilayah: String = ""
)

fun UpdatebeastUiEvent.toHewan(): Hewan = Hewan(
    idHewan = idHewan,
    namaHewan = namaHewan,
    tipePakan = tipePakan,
    populasi = populasi,
    zonaWilayah = zonaWilayah
)

fun Hewan.toUpdatebeastUiEvent(): UpdatebeastUiEvent = UpdatebeastUiEvent(
    idHewan = idHewan,
    namaHewan = namaHewan,
    tipePakan = tipePakan,
    populasi = populasi,
    zonaWilayah = zonaWilayah
)
