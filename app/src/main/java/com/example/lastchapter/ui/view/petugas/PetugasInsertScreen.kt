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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.petugas.InsertPetugasViewModel
import com.example.lastchapter.ui.viewmodel.petugas.InsertoffUiEvent
import com.example.lastchapter.ui.viewmodel.petugas.InsertoffUiState
import kotlinx.coroutines.launch

object DestinasiInsertPetugas : DestinasiNavigasi {
    override val route = "insert_petugas"
    override val titleRes = "Insert Petugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPetugasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPetugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPetugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onPetugasValueChange = viewModel::updateInsertPetugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPetugas() // Or adjust the function to insert Petugas
                    navigateBack()
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
fun EntryBody(
    insertUiState: InsertoffUiState,
    onPetugasValueChange: (InsertoffUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertoffUiEvent,
            onValueChange = onPetugasValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertoffUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertoffUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val jabatanList = listOf("Keeper", "Dokter Hewan", "Kurator")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ID Petugas First
        OutlinedTextField(
            value = insertUiEvent.idPetugas,
            onValueChange = { onValueChange(insertUiEvent.copy(idPetugas = it)) },
            label = { Text("ID Petugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Nama Petugas Second
        OutlinedTextField(
            value = insertUiEvent.namaPetugas,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPetugas = it)) },
            label = { Text("Nama Petugas") },
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
                    selected = insertUiEvent.jabatan == jabatan,
                    onClick = { onValueChange(insertUiEvent.copy(jabatan = jabatan)) },
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

