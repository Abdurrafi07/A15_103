package com.example.lastchapter.ui.viewmodel.kandang

import com.example.lastchapter.model.Kandang
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.KandangRepository
import kotlinx.coroutines.launch

class InsertKandangViewModel(private val kandangRepository: KandangRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertcageUiState())
        private set

    fun updateInsertKandangState(insertcageUiEvent: InsertcageUiEvent) {
        uiState = InsertcageUiState(insertcageUiEvent = insertcageUiEvent)
    }

    suspend fun insertKandang() {
        viewModelScope.launch {
            try {
                kandangRepository.insertKandang(uiState.insertcageUiEvent.toKandang())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertcageUiState(
    val insertcageUiEvent: InsertcageUiEvent = InsertcageUiEvent()
)

data class InsertcageUiEvent(
    val idKandang: String = "",
    val kapasitas: String = "",
    val lokasi: String = ""
)

fun InsertcageUiEvent.toKandang(): Kandang = Kandang(
    idKandang = idKandang,
    kapasitas = kapasitas,
    lokasi = lokasi
)

fun Kandang.toUiStateKandang(): InsertcageUiState = InsertcageUiState(
    insertcageUiEvent = toInsertcageUiEvent()
)

fun Kandang.toInsertcageUiEvent(): InsertcageUiEvent = InsertcageUiEvent(
    idKandang = idKandang,
    kapasitas = kapasitas,
    lokasi = lokasi
)
