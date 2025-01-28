package com.example.lastchapter.ui.view.kandang

import com.example.lastchapter.ui.viewmodel.kandang.DetailcageUiState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.example.lastchapter.model.Kandang
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.kandang.DetailKandangViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults

object DestinasiDetailKandang : DestinasiNavigasi {
    override val route = "detail_kandang"
    override val titleRes = "Detail Kandang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKandangScreen(
    idKandang: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailKandangViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.cageuiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getKandangById(idKandang)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKandang.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKandangById(idKandang)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Kandang")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailcageUiState.Loading -> {
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailcageUiState.Success -> {
                val kandang = (uiState as DetailcageUiState.Success).kandang
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    ItemDetailKandang(kandang = kandang)
                }
            }
            is DetailcageUiState.Error -> {
                Text(
                    text = "Failed to load data",
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ItemDetailKandang(
    modifier: Modifier = Modifier,
    kandang: Kandang
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
            ComponentDetailKandang(judul = "ID Kandang", isinya = kandang.idKandang)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailKandang(judul = "Kapasitas", isinya = kandang.kapasitas)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailKandang(judul = "Lokasi", isinya = kandang.lokasi)
        }
    }
}

@Composable
fun ComponentDetailKandang(
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