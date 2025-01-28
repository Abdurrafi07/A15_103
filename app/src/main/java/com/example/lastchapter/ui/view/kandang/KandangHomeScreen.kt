package com.example.lastchapter.ui.view.kandang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lastchapter.model.Kandang
import com.example.lastchapter.ui.customwidget.CostumeTopAppBar
import com.example.lastchapter.ui.navigation.DestinasiNavigasi
import com.example.lastchapter.ui.view.hewan.OnError
import com.example.lastchapter.ui.view.hewan.OnLoading
import com.example.lastchapter.ui.viewmodel.PenyediaViewModel
import com.example.lastchapter.ui.viewmodel.kandang.HomeKandangViewModel
import com.example.lastchapter.ui.viewmodel.kandang.HomecageUiState

object DestinasiHomeKandang: DestinasiNavigasi {
    override val route = "home_kandang"
    override val titleRes = "Home Kandang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKandangScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
    viewModel: HomeKandangViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Kandang",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getKandang() },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kandang")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    viewModel.searchKandang(it)
                }
            )
            HomeStatus(
                homeUiState = viewModel.cageUIState,
                retryAction = { viewModel.getKandang() },
                onDetailClick = onDetailClick,
                onDeleteClick = { idKandang -> viewModel.deleteKandang(idKandang) }
            )
        }
    }
}


@Composable
fun SearchBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        placeholder = { Text("Search Kandang...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun HomeStatus(
    homeUiState: HomecageUiState,
    retryAction: () -> Unit,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomecageUiState.Loading -> {
            OnLoading(modifier = Modifier.fillMaxSize())
        }
        is HomecageUiState.Success -> {
            if (homeUiState.kandang.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kandang")
                }
            } else {
                KandangLayout(
                    kandang = homeUiState.kandang,
                    onDetailClick = { onDetailClick(it.idKandang) },
                    onDeleteClick = { onDeleteClick(it.idKandang) }
                )
            }
        }
        is HomecageUiState.Error -> {
            OnError(retryAction, modifier = Modifier.fillMaxSize())
        }
    }
}


@Composable
fun KandangLayout(
    kandang: List<Kandang>,
    onDetailClick: (Kandang) -> Unit,
    onDeleteClick: (Kandang) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kandang) { kandang ->
            KandangCard(
                kandang = kandang,
                onDetailClick = { onDetailClick(kandang) },
                onDeleteClick = { onDeleteClick(kandang) }
            )
        }
    }
}


@Composable
fun KandangCard(
    kandang: Kandang,
    onDetailClick: (Kandang) -> Unit,
    onDeleteClick: (Kandang) -> Unit
) {
    var openDeleteDialog by remember { mutableStateOf(false) }

    // Dialog for confirming delete
    if (openDeleteDialog) {
        AlertDialog(
            onDismissRequest = { openDeleteDialog = false },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Apakah Anda yakin ingin menghapus kandang ini?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(kandang)  // Call the delete function
                    openDeleteDialog = false
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                // Displaying the ID first, then kapasitas, and finally lokasi
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "ID: ${kandang.idKandang}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text("Kapasitas: ${kandang.kapasitas}", style = MaterialTheme.typography.bodyMedium)
                    Text("Lokasi: ${kandang.lokasi}", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.weight(1f))

                // Stack the icons vertically
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { onDetailClick(kandang) }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Detail")
                    }
                    IconButton(onClick = { openDeleteDialog = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}