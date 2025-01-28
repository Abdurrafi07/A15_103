package com.example.lastchapter.ui.view.hewan

import com.example.lastchapter.ui.viewmodel.hewan.HomebeastUiState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.R
import com.example.lastchapter.model.Hewan
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.hewan.HomeHewanViewModel

object DestinasiHomeHewan : DestinasiNavigasi {
    override val route = "home_hewan"
    override val titleRes = "Home Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHewanScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val showDialog = remember { mutableStateOf(false) }
    val selectedHewan = remember { mutableStateOf<Hewan?>(null) }
    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                CostumeTopAppBar(
                    title = DestinasiHomeHewan.titleRes,
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    onRefresh = { viewModel.getHewan() },
                    navigateUp = navigateBack
                )
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text("Cari Hewan...") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Hewan")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.beastUIState,
            retryAction = { viewModel.getHewan() },
            modifier = Modifier.padding(innerPadding),
            searchQuery = searchQuery.value,
            onDetailClick = onDetailClick,
            onDeleteClick = { hewan ->
                selectedHewan.value = hewan
                showDialog.value = true
            }
        )
    }

    if (showDialog.value && selectedHewan.value != null) {
        DeleteDialog(
            onDismiss = { showDialog.value = false },
            onDelete = {
                viewModel.deleteHewan(selectedHewan.value!!.idHewan)
                viewModel.getHewan()
                showDialog.value = false
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomebeastUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    searchQuery: String,
    onDeleteClick: (Hewan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomebeastUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomebeastUiState.Success -> {
            val filteredHewan = homeUiState.hewan.filter {
                it.namaHewan.contains(searchQuery, ignoreCase = true)
            }
            if (filteredHewan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Hewan")
                }
            } else {
                HewanLayout(
                    hewan = filteredHewan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idHewan) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomebeastUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Konfirmasi Hapus")
        },
        text = {
            Text(text = "Apakah Anda yakin ingin menghapus data ini?")
        },
        confirmButton = {
            Button(onClick = onDelete) {
                Text("Hapus")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

@Composable
fun HomeStatus(
    homeUiState: HomebeastUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Hewan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomebeastUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomebeastUiState.Success -> {
            if (homeUiState.hewan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Hewan")
                }
            } else {
                HewanLayout(
                    hewan = homeUiState.hewan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idHewan) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomebeastUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun HewanLayout(
    hewan: List<Hewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Hewan) -> Unit,
    onDeleteClick: (Hewan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(hewan) { hewan ->
            HewanCard(
                hewan = hewan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(hewan) },
                onDeleteClick = { onDeleteClick(hewan) }
            )
        }
    }
}

@Composable
fun HewanCard(
    hewan: Hewan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Hewan) -> Unit = {}
) {
    // Determine background color based on tipePakan
    val containerColor = when (hewan.tipePakan) {
        "Karnivora" -> Color(0xFFFF5733).copy(alpha = 0.2f) // Light Red with transparency
        "Herbivora" -> Color(0xFF28A745).copy(alpha = 0.2f) // Light Green with transparency
        "Omnivora" -> Color(0xFF17A2B8).copy(alpha = 0.2f) // Light Blue with transparency
        else -> MaterialTheme.colorScheme.background // Default background color
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor) // Set background color
        ) {
            // Card Content
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = hewan.namaHewan,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = hewan.idHewan,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = "Tipe Pakan: ${hewan.tipePakan}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Populasi: ${hewan.populasi}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Zona Wilayah: ${hewan.zonaWilayah}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Fixed Delete Icon Position at Bottom End
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Change alignment to BottomEnd
                    .padding(8.dp),
                onClick = { onDeleteClick(hewan) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Hewan",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
