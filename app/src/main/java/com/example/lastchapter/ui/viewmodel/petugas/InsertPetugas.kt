package com.example.lastchapter.ui.viewmodel.petugas

import com.example.lastchapter.model.Petugas
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.repository.PetugasRepository
import kotlinx.coroutines.launch

class InsertPetugasViewModel(private val petugasRepository: PetugasRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertoffUiState())
        private set

    fun updateInsertPetugasState(insertoffUiEvent: InsertoffUiEvent) {
        uiState = InsertoffUiState(insertoffUiEvent = insertoffUiEvent)
    }

    suspend fun insertPetugas() {
        viewModelScope.launch {
            try {
                petugasRepository.insertPetugas(uiState.insertoffUiEvent.toPetugas())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertoffUiState(
    val insertoffUiEvent: InsertoffUiEvent = InsertoffUiEvent()
)

data class InsertoffUiEvent(
    val idPetugas: String = "",
    val namaPetugas: String = "",
    val jabatan: String = ""
)

fun InsertoffUiEvent.toPetugas(): Petugas = Petugas(
    idPetugas = idPetugas,
    namaPetugas = namaPetugas,
    jabatan = jabatan
)

fun Petugas.toUiStatePetugas(): InsertoffUiState = InsertoffUiState(
    insertoffUiEvent = toInsertoffUiEvent()
)

fun Petugas.toInsertoffUiEvent(): InsertoffUiEvent = InsertoffUiEvent(
    idPetugas = idPetugas,
    namaPetugas = namaPetugas,
    jabatan = jabatan
)
