package com.example.lastchapter.ui.viewmodel.petugas

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastchapter.model.Petugas
import com.example.lastchapter.repository.PetugasRepository
import kotlinx.coroutines.launch

class UpdatePetugasViewModel(private val petugasRepository:
                      PetugasRepository
) : ViewModel() {
    var offuiState by mutableStateOf(UpdateoffUiState())
        private set

    fun loadPetugas(idPetugas: String) {
        viewModelScope.launch {
            try {
                val petugas = petugasRepository.getPetugasById(idPetugas)
                offuiState = UpdateoffUiState(updateoffUiEvent = petugas.toUpdateoffUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePetugasState(updateoffUiEvent: UpdateoffUiEvent) {
        offuiState = UpdateoffUiState(updateoffUiEvent = updateoffUiEvent)
    }

    suspend fun updatePetugas(idPetugas: String) {
        viewModelScope.launch {
            try {
                petugasRepository.updatePetugas(idPetugas, offuiState.updateoffUiEvent.toPetugas())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateoffUiState(
    val updateoffUiEvent: UpdateoffUiEvent = UpdateoffUiEvent()
)

data class UpdateoffUiEvent(
    val idPetugas: String = "",
    val namaPetugas: String = "",
    val jabatan: String = ""
)

fun UpdateoffUiEvent.toPetugas(): Petugas = Petugas(
    idPetugas = idPetugas,
    namaPetugas = namaPetugas,
    jabatan = jabatan
)

fun Petugas.toUpdateoffUiEvent(): UpdateoffUiEvent = UpdateoffUiEvent(
    idPetugas = idPetugas,
    namaPetugas = namaPetugas,
    jabatan = jabatan
)
