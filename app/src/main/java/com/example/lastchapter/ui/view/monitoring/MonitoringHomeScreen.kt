package com.example.lastchapter.ui.view.monitoring

import com.example.lastchapter.ui.view.hewan.OnError
import com.example.lastchapter.ui.view.hewan.OnLoading
import com.example.lastchapter.ui.viewmodel.monitoring.HomemonUiState
import com.example.lastchapter.ui.viewmodel.monitoring.HomeMonitoringViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.R
import com.example.lastchapter.model.Monitoring
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel

object DestinasiHomeMonitoring : DestinasiNavigasi {
    override val route = "home_monitoring"
    override val titleRes = "Home Monitoring"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMonitoringScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
    viewModel: HomeMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val showDialog = remember { mutableStateOf(false) }
    val selectedMonitoring = remember { mutableStateOf<Monitoring?>(null) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Monitoring",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getMonitoring() },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Monitoring")
            }
        }
    ) { innerPadding ->
        HomeMonitoringStatus(
            homeUiState = viewModel.monUIState,
            retryAction = { viewModel.getMonitoring() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { monitoring ->
                selectedMonitoring.value = monitoring
                showDialog.value = true
            }
        )
    }

    if (showDialog.value && selectedMonitoring.value != null) {
        DeleteDialog(
            onDismiss = { showDialog.value = false },
            onDelete = {
                viewModel.deleteMonitoring(selectedMonitoring.value!!.idMonitoring)
                viewModel.getMonitoring()
                showDialog.value = false
            }
        )
    }
}

@Composable
fun HomeMonitoringStatus(
    homeUiState: HomemonUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Monitoring) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomemonUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomemonUiState.Success -> {
            if (homeUiState.monitoring.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Monitoring")
                }
            } else {
                MonitoringLayout(
                    monitoring = homeUiState.monitoring,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idMonitoring) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomemonUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun MonitoringLayout(
    monitoring: List<Monitoring>,
    modifier: Modifier = Modifier,
    onDetailClick: (Monitoring) -> Unit,
    onDeleteClick: (Monitoring) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(monitoring) { monitoringItem ->
            MonitoringCard(
                monitoring = monitoringItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(monitoringItem) },
                onDeleteClick = { onDeleteClick(monitoringItem) }
            )
        }
    }
}

@Composable
fun MonitoringCard(
    monitoring: Monitoring,
    modifier: Modifier = Modifier,
    onDeleteClick: (Monitoring) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monitoring ID: ${monitoring.idMonitoring}",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(monitoring) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = "Tanggal Monitoring: ${monitoring.tanggalMonitoring}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Hewan Sakit: ${monitoring.hewanSakit}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Hewan Sehat: ${monitoring.hewanSehat}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status: ${monitoring.status}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Penghapusan") },
        text = { Text("Apakah Anda yakin ingin menghapus Monitoring ini?") },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text("Hapus")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
