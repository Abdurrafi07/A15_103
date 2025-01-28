package com.example.lastchapter.ui.view.petugas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.petugas.UpdatePetugasViewModel
import com.example.lastchapter.ui.viewmodel.petugas.UpdateoffUiEvent
import com.example.lastchapter.ui.viewmodel.petugas.UpdateoffUiState
import kotlinx.coroutines.launch

object DestinasiUpdatePetugas : DestinasiNavigasi {
    override val route = "update_petugas"
    override val titleRes = "Update Petugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePetugasScreen(
    idPetugas: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePetugasViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idPetugas) {
        viewModel.loadPetugas(idPetugas)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePetugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.offuiState,
            onPetugasValueChange = viewModel::updatePetugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePetugas(idPetugas)
                    navigateBack()  // Navigate back after saving
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdateBody(
    updateUiState: UpdateoffUiState,
    onPetugasValueChange: (UpdateoffUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiState.updateoffUiEvent,
            onValueChange = onPetugasValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    updateUiEvent: UpdateoffUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateoffUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val jabatanList = listOf("Keeper", "Dokter Hewan", "Kurator")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Nama Petugas First
        OutlinedTextField(
            value = updateUiEvent.namaPetugas,
            onValueChange = { onValueChange(updateUiEvent.copy(namaPetugas = it)) },
            label = { Text("Nama Petugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // ID Petugas Second
        OutlinedTextField(
            value = updateUiEvent.idPetugas,
            onValueChange = { onValueChange(updateUiEvent.copy(idPetugas = it)) },
            label = { Text("ID Petugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Jabatan Radio Buttons Last
        Text(text = "Pilih Jabatan Petugas")
        jabatanList.forEach { jabatan ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
            ) {
                RadioButton(
                    selected = updateUiEvent.jabatan == jabatan,
                    onClick = { onValueChange(updateUiEvent.copy(jabatan = jabatan)) },
                    enabled = enabled
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = jabatan)
            }
        }

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

