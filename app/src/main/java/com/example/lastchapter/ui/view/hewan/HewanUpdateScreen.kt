package com.example.lastchapter.ui.view.hewan

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
import com.example.lastchapter.ui.viewmodel.hewan.UpdateHewanViewModel
import com.example.lastchapter.ui.viewmodel.hewan.UpdatebeastUiEvent
import com.example.lastchapter.ui.viewmodel.hewan.UpdatebeastUiState
import com.example.lastchapter.ui.widget.DynamicSelectedTextField
import kotlinx.coroutines.launch

object DestinasiUpdateHewan : DestinasiNavigasi {
    override val route = "update_hewan"
    override val titleRes = "Update Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateHewanScreen(
    idHewan: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateHewanViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idHewan) {
        viewModel.loadHewan(idHewan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateHewan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.beastUiState,
            onHewanValueChange = viewModel::updateHewanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateHewan(idHewan)
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
    updateUiState: UpdatebeastUiState,
    onHewanValueChange: (UpdatebeastUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiState.updatebeastUiEvent,
            onValueChange = onHewanValueChange,
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
    updateUiEvent: UpdatebeastUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatebeastUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val items = listOf("Karnivora", "Omnivora", "Herbivora")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input for ID Hewan
        OutlinedTextField(
            value = updateUiEvent.idHewan,
            onValueChange = { onValueChange(updateUiEvent.copy(idHewan = it)) },
            label = { Text("ID Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input for Nama Hewan
        OutlinedTextField(
            value = updateUiEvent.namaHewan,
            onValueChange = { onValueChange(updateUiEvent.copy(namaHewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown for Tipe Pakan
        DynamicSelectedTextField(
            selectedValue = updateUiEvent.tipePakan,
            options = items,
            label = "Tipe Pakan",
            onValueChangedEvent = { value ->
                onValueChange(updateUiEvent.copy(tipePakan = value))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for Populasi
        OutlinedTextField(
            value = updateUiEvent.populasi,
            onValueChange = { onValueChange(updateUiEvent.copy(populasi = it)) },
            label = { Text("Populasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input for Zona Wilayah
        OutlinedTextField(
            value = updateUiEvent.zonaWilayah,
            onValueChange = { onValueChange(updateUiEvent.copy(zonaWilayah = it)) },
            label = { Text("Zona Wilayah") },
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
