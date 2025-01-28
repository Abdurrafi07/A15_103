package com.example.lastchapter.ui.view.monitoring

import com.example.lastchapter.ui.viewmodel.monitoring.DetailmonUiState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.model.Monitoring
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.monitoring.DetailMonitoringViewModel

object DestinasiDetailMonitoring : DestinasiNavigasi {
    override val route = "detail_monitoring"
    override val titleRes = "Detail Monitoring"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMonitoringScreen(
    idMonitoring: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.monUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getMonitoringById(idMonitoring)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailMonitoring.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMonitoringById(idMonitoring)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Monitoring")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailmonUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailmonUiState.Success -> {
                val monitoring = (uiState as DetailmonUiState.Success).monitoring
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailMonitoring(monitoring = monitoring)
                }
            }
            is DetailmonUiState.Error -> {
                Text(
                    text = "Gagal memuat data",
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ItemDetailMonitoring(
    modifier: Modifier = Modifier,
    monitoring: Monitoring
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMonitoring(judul = "ID Monitoring", isinya = monitoring.idMonitoring)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMonitoring(judul = "Tanggal Monitoring", isinya = monitoring.tanggalMonitoring)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMonitoring(judul = "Hewan Sakit", isinya = monitoring.hewanSakit)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMonitoring(judul = "Hewan Sehat", isinya = monitoring.hewanSehat)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMonitoring(judul = "Status", isinya = monitoring.status)
        }
    }
}

@Composable
fun ComponentDetailMonitoring(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        )
    }
}
