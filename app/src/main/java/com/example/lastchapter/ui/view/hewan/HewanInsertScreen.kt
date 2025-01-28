package com.example.lastchapter.ui.view.hewan

import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.hewan.InsertHewanViewModel
import com.example.lastchapter.ui.viewmodel.hewan.InsertbeastUiEvent
import com.example.lastchapter.ui.viewmodel.hewan.InsertbeastUiState
import com.example.lastchapter.ui.widget.DynamicSelectedTextField
import kotlinx.coroutines.launch

object DestinasiInsertHewan : DestinasiNavigasi {
    override val route = "insert_hewan"
    override val titleRes = "Insert Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertHewanScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertHewan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onHewanValueChange = viewModel::updateInsertHewanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertHewan()
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
    insertUiState: InsertbeastUiState,
    onHewanValueChange: (InsertbeastUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertbeastUiEvent,
            onValueChange = onHewanValueChange,
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
    insertUiEvent: InsertbeastUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertbeastUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val items = listOf("Karnivora", "Omnivora", "Herbivora")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input for ID Hewan
        OutlinedTextField(
            value = insertUiEvent.idHewan,
            onValueChange = { onValueChange(insertUiEvent.copy(idHewan = it)) },
            label = { Text("ID Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input for Nama Hewan
        OutlinedTextField(
            value = insertUiEvent.namaHewan,
            onValueChange = { onValueChange(insertUiEvent.copy(namaHewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown for Tipe Pakan
        DynamicSelectedTextField(
            selectedValue = insertUiEvent.tipePakan,
            options = items,
            label = "Tipe Pakan",
            onValueChangedEvent = { value ->
                onValueChange(insertUiEvent.copy(tipePakan = value))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for Populasi
        OutlinedTextField(
            value = insertUiEvent.populasi,
            onValueChange = { onValueChange(insertUiEvent.copy(populasi = it)) },
            label = { Text("Populasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input for Zona Wilayah
        OutlinedTextField(
            value = insertUiEvent.zonaWilayah,
            onValueChange = { onValueChange(insertUiEvent.copy(zonaWilayah = it)) },
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