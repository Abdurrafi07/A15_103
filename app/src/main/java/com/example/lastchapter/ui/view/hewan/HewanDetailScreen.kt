package com.example.lastchapter.ui.view.hewan

import com.example.lastchapter.ui.viewmodel.hewan.DetailbeastUiState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.model.Hewan
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.hewan.DetailHewanViewModel

object DestinasiDetailHewan : DestinasiNavigasi {
    override val route = "detail_hewan"
    override val titleRes = "Detail Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHewanScreen(
    idHewan: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.beastuiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getHewanById(idHewan)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailHewan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getHewanById(idHewan)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Hewan")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailbeastUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailbeastUiState.Success -> {
                val hewan = (uiState as DetailbeastUiState.Success).hewan
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailHewan(hewan = hewan)
                }
            }
            is DetailbeastUiState.Error -> {
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
fun ItemDetailHewan(
    modifier: Modifier = Modifier,
    hewan: Hewan
) {
    val backgroundColor = when (hewan.tipePakan) {
        "Karnivora" -> Color(0xFFFF5733).copy(alpha = 0.2f) // Light Red with transparency
        "Herbivora" -> Color(0xFF28A745).copy(alpha = 0.2f) // Light Green with transparency
        "Omnivora" -> Color(0xFF17A2B8).copy(alpha = 0.2f) // Light Blue with transparency
        else -> MaterialTheme.colorScheme.surface // Default surface color
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Corrected Component for Hewan
            ComponentDetailHewan(judul = "ID Hewan", isinya = hewan.idHewan)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailHewan(judul = "Nama Hewan", isinya = hewan.namaHewan)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailHewan(judul = "Tipe Pakan", isinya = hewan.tipePakan)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailHewan(judul = "Populasi", isinya = hewan.populasi)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailHewan(judul = "Zona Wilayah", isinya = hewan.zonaWilayah)
        }
    }
}


@Composable
fun ComponentDetailHewan(
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
