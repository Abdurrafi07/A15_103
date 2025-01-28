package com.example.lastchapter.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lastchapter.SatoApp
import com.example.lastchapter.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.lastchapter.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.lastchapter.ui.viewmodel.hewan.InsertHewanViewModel
import com.example.lastchapter.ui.viewmodel.hewan.UpdateHewanViewModel
import com.example.lastchapter.ui.viewmodel.kandang.DetailKandangViewModel
import com.example.lastchapter.ui.viewmodel.kandang.HomeKandangViewModel
import com.example.lastchapter.ui.viewmodel.kandang.InsertKandangViewModel
import com.example.lastchapter.ui.viewmodel.kandang.UpdateKandangViewModel
import com.example.lastchapter.ui.viewmodel.monitoring.DetailMonitoringViewModel
import com.example.lastchapter.ui.viewmodel.monitoring.HomeMonitoringViewModel
import com.example.lastchapter.ui.viewmodel.monitoring.InsertMonitoringViewModel
import com.example.lastchapter.ui.viewmodel.monitoring.UpdateMonitoringViewModel
import com.example.lastchapter.ui.viewmodel.petugas.DetailPetugasViewModel
import com.example.lastchapter.ui.viewmodel.petugas.HomePetugasViewModel
import com.example.lastchapter.ui.viewmodel.petugas.InsertPetugasViewModel
import com.example.lastchapter.ui.viewmodel.petugas.UpdatePetugasViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePetugasViewModel(SatoApplications().container.petugasRepository) }
        initializer { InsertPetugasViewModel(SatoApplications().container.petugasRepository) }
        initializer { UpdatePetugasViewModel(SatoApplications().container.petugasRepository) }
        initializer { DetailPetugasViewModel(SatoApplications().container.petugasRepository) }
        initializer { HomeHewanViewModel(SatoApplications().container.hewanRepository) }
        initializer { InsertHewanViewModel(SatoApplications().container.hewanRepository) }
        initializer { UpdateHewanViewModel(SatoApplications().container.hewanRepository) }
        initializer { DetailHewanViewModel(SatoApplications().container.hewanRepository) }
        initializer { HomeKandangViewModel(SatoApplications().container.kandangRepository) }
        initializer { InsertKandangViewModel(SatoApplications().container.kandangRepository) }
        initializer { UpdateKandangViewModel(SatoApplications().container.kandangRepository) }
        initializer { DetailKandangViewModel(SatoApplications().container.kandangRepository) }
        initializer { HomeMonitoringViewModel(SatoApplications().container.monitoringRepository) }
        initializer { InsertMonitoringViewModel(SatoApplications().container.monitoringRepository) }
        initializer { UpdateMonitoringViewModel(SatoApplications().container.monitoringRepository) }
        initializer { DetailMonitoringViewModel(SatoApplications().container.monitoringRepository) }
    }

    // Fungsi untuk mengambil aplikasi konteks
    fun CreationExtras.SatoApplications(): SatoApp =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SatoApp)
}
