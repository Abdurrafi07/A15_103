package com.example.lastchapter.ui.view.kandang

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.kandang.UpdateKandangViewModel
import com.example.lastchapter.ui.viewmodel.kandang.UpdatecageUiEvent
import com.example.lastchapter.ui.viewmodel.kandang.UpdatecageUiState
import kotlinx.coroutines.launch

object DestinasiUpdateKandang : DestinasiNavigasi {
    override val route = "update_kandang"
    override val titleRes = "Update Kandang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKandangScreen(
    idKandang: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateKandangViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idKandang) {
        viewModel.loadKandang(idKandang)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKandang.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.cageUiState,
            onKandangValueChange = viewModel::updateKandangState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKandang(idKandang)
                    navigateBack() // Navigate back after saving
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
    updateUiState: UpdatecageUiState,
    onKandangValueChange: (UpdatecageUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiState.updatecageUiEvent,
            onValueChange = onKandangValueChange,
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
    updateUiEvent: UpdatecageUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatecageUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input for ID Kandang
        OutlinedTextField(
            value = updateUiEvent.idKandang,
            onValueChange = { onValueChange(updateUiEvent.copy(idKandang = it)) },
            label = { Text("ID Kandang") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Input for Kapasitas
        OutlinedTextField(
            value = updateUiEvent.kapasitas,
            onValueChange = { onValueChange(updateUiEvent.copy(kapasitas = it)) },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Input for Lokasi
        OutlinedTextField(
            value = updateUiEvent.lokasi,
            onValueChange = { onValueChange(updateUiEvent.copy(lokasi = it)) },
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
