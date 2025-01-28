package com.example.lastchapter.ui.view.monitoring

import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.monitoring.InsertMonitoringViewModel
import com.example.lastchapter.ui.viewmodel.monitoring.InsertmonUiEvent
import com.example.lastchapter.ui.viewmodel.monitoring.InsertmonUiState
import kotlinx.coroutines.launch

object DestinasiInsertMonitoring : DestinasiNavigasi {
    override val route = "insert_monitoring"
    override val titleRes = "Insert Monitoring"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMonitoringScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertMonitoring.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onMonitoringValueChange = viewModel::updateInsertMonitoringState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMonitoring()
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
    insertUiState: InsertmonUiState,
    onMonitoringValueChange: (InsertmonUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertmonUiEvent,
            onValueChange = onMonitoringValueChange,
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
    insertUiEvent: InsertmonUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertmonUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idMonitoring,
            onValueChange = { onValueChange(insertUiEvent.copy(idMonitoring = it)) },
            label = { Text("ID Monitoring") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalMonitoring,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalMonitoring = it)) },
            label = { Text("Tanggal Monitoring") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.hewanSakit,
            onValueChange = { onValueChange(insertUiEvent.copy(hewanSakit = it)) },
            label = { Text("Hewan Sakit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.hewanSehat,
            onValueChange = { onValueChange(insertUiEvent.copy(hewanSehat = it)) },
            label = { Text("Hewan Sehat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.status,
            onValueChange = { onValueChange(insertUiEvent.copy(status = it)) },
            label = { Text("Status") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
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
