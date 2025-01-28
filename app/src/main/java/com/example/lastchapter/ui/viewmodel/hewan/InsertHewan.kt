package com.example.lastchapter.ui.viewmodel.hewan

import com.example.lastchapter.model.Hewan
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.HewanRepository
import kotlinx.coroutines.launch

class InsertHewanViewModel(private val hewanRepository: HewanRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertbeastUiState())
        private set

    fun updateInsertHewanState(insertbeastUiEvent: InsertbeastUiEvent) {
        uiState = InsertbeastUiState(insertbeastUiEvent = insertbeastUiEvent)
    }

    suspend fun insertHewan() {
        viewModelScope.launch {
            try {
                hewanRepository.insertHewan(uiState.insertbeastUiEvent.toHewan())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertbeastUiState(
    val insertbeastUiEvent: InsertbeastUiEvent = InsertbeastUiEvent()
)

data class InsertbeastUiEvent(
    val idHewan: String = "",
    val namaHewan: String = "",
    val tipePakan: String = "",
    val populasi: String = "",
    val zonaWilayah: String = ""
)

fun InsertbeastUiEvent.toHewan(): Hewan = Hewan(
    idHewan = idHewan,
    namaHewan = namaHewan,
    tipePakan = tipePakan,
    populasi = populasi,
    zonaWilayah = zonaWilayah
)

fun Hewan.toUiStateHewan(): InsertbeastUiState = InsertbeastUiState(
    insertbeastUiEvent = toInsertbeastUiEvent()
)

fun Hewan.toInsertbeastUiEvent(): InsertbeastUiEvent = InsertbeastUiEvent(
    idHewan = idHewan,
    namaHewan = namaHewan,
    tipePakan = tipePakan,
    populasi = populasi,
    zonaWilayah = zonaWilayah
)