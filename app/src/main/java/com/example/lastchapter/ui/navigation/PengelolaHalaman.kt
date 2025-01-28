package com.example.lastchapter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lastchapter.ui.view.DestinasiHome
import com.example.lastchapter.ui.view.SplashHomeViews
import com.example.lastchapter.ui.view.hewan.DestinasiDetailHewan
import com.example.lastchapter.ui.view.hewan.DestinasiHomeHewan
import com.example.lastchapter.ui.view.hewan.DestinasiInsertHewan
import com.example.lastchapter.ui.view.hewan.DestinasiUpdateHewan
import com.example.lastchapter.ui.view.hewan.DetailHewanScreen
import com.example.lastchapter.ui.view.hewan.HomeHewanScreen
import com.example.lastchapter.ui.view.hewan.InsertHewanScreen
import com.example.lastchapter.ui.view.hewan.UpdateHewanScreen
import com.example.lastchapter.ui.view.kandang.DestinasiDetailKandang
import com.example.lastchapter.ui.view.kandang.DestinasiHomeKandang
import com.example.lastchapter.ui.view.kandang.DestinasiInsertKandang
import com.example.lastchapter.ui.view.kandang.DestinasiUpdateKandang
import com.example.lastchapter.ui.view.kandang.DetailKandangScreen
import com.example.lastchapter.ui.view.kandang.HomeKandangScreen
import com.example.lastchapter.ui.view.kandang.InsertKandangScreen
import com.example.lastchapter.ui.view.kandang.UpdateKandangScreen
import com.example.lastchapter.ui.view.monitoring.DestinasiDetailMonitoring
import com.example.lastchapter.ui.view.monitoring.DestinasiHomeMonitoring
import com.example.lastchapter.ui.view.monitoring.DestinasiInsertMonitoring
import com.example.lastchapter.ui.view.monitoring.DestinasiUpdateMonitoring
import com.example.lastchapter.ui.view.monitoring.DetailMonitoringScreen
import com.example.lastchapter.ui.view.monitoring.HomeMonitoringScreen
import com.example.lastchapter.ui.view.monitoring.InsertMonitoringScreen
import com.example.lastchapter.ui.view.monitoring.UpdateMonitoringScreen
import com.example.lastchapter.ui.view.petugas.DestinasiDetailPetugas
import com.example.lastchapter.ui.view.petugas.DestinasiHomePetugas
import com.example.lastchapter.ui.view.petugas.DestinasiInsertPetugas
import com.example.lastchapter.ui.view.petugas.DestinasiUpdatePetugas
import com.example.lastchapter.ui.view.petugas.DetailPetugasScreen
import com.example.lastchapter.ui.view.petugas.HomePetugasScreen
import com.example.lastchapter.ui.view.petugas.InsertPetugasScreen
import com.example.lastchapter.ui.view.petugas.UpdatePetugasScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Home Screen for displaying the animal list
        composable(DestinasiHome.route) {
            SplashHomeViews(
                onPetugasClick = {
                    navController.navigate(DestinasiHomePetugas.route)
                },
                onHewanClick = {
                    navController.navigate(DestinasiHomeHewan.route)
                },
                onKandangClick = {
                    navController.navigate(DestinasiHomeKandang.route)
                },
                onMonitoringClick = {
                    navController.navigate(DestinasiHomeMonitoring.route)
                }
            )
        }

        // PETUGAS
        composable(DestinasiHomePetugas.route) {
            HomePetugasScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertPetugas.route)
                },
                onDetailClick = { idPetugas ->
                    if (idPetugas.isNotEmpty()){
                        navController.navigate("${DestinasiDetailPetugas.route}/$idPetugas")
                    }
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinasiInsertPetugas.route) {
            InsertPetugasScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePetugas.route) {
                        popUpTo(DestinasiHomePetugas.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetailPetugas.route}/{idPetugas}",
            arguments = listOf(navArgument("idPetugas") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPetugas = backStackEntry.arguments?.getString("idPetugas") ?: ""
            DetailPetugasScreen(
                idPetugas = idPetugas,
                navigateBack = {
                    navController.navigate(DestinasiHomePetugas.route) {
                        popUpTo(DestinasiHomePetugas.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdatePetugas.route}/$idPetugas")
                }
            )
        }

        composable(
            route = "${DestinasiUpdatePetugas.route}/{idPetugas}",
            arguments = listOf(navArgument("idPetugas") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPetugas = backStackEntry.arguments?.getString("idPetugas") ?: ""
            UpdatePetugasScreen(
                idPetugas = idPetugas,
                navigateBack = {
                    navController.navigate(DestinasiHomePetugas.route) {
                        popUpTo(DestinasiHomePetugas.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // HEWAN
        composable(DestinasiHomeHewan.route) {
            HomeHewanScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertHewan.route)
                },
                onDetailClick = { idHewan ->
                    if (idHewan.isNotEmpty()){
                        navController.navigate("${DestinasiDetailHewan.route}/$idHewan")
                    }
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinasiInsertHewan.route) {
            InsertHewanScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeHewan.route) {
                        popUpTo(DestinasiHomeHewan.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetailHewan.route}/{idHewan}",
            arguments = listOf(navArgument("idHewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idHewan = backStackEntry.arguments?.getString("idHewan") ?: ""
            DetailHewanScreen(
                idHewan = idHewan,
                navigateBack = {
                    navController.navigate(DestinasiHomeHewan.route) {
                        popUpTo(DestinasiHomeHewan.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateHewan.route}/$idHewan")
                }
            )
        }

        composable(
            route = "${DestinasiUpdateHewan.route}/{idHewan}",
            arguments = listOf(navArgument("idHewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idHewan = backStackEntry.arguments?.getString("idHewan") ?: ""
            UpdateHewanScreen(
                idHewan = idHewan,
                navigateBack = {
                    navController.navigate(DestinasiHomeHewan.route) {
                        popUpTo(DestinasiHomeHewan.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        // KANDANG
        composable(DestinasiHomeKandang.route) {
            HomeKandangScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertKandang.route)
                },
                onDetailClick = { idKandang ->
                    if (idKandang.isNotEmpty()){
                        navController.navigate("${DestinasiDetailKandang.route}/$idKandang")
                    }
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinasiInsertKandang.route) {
            InsertKandangScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKandang.route) {
                        popUpTo(DestinasiHomeKandang.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetailKandang.route}/{idKandang}",
            arguments = listOf(navArgument("idKandang") { type = NavType.StringType })
        ) { backStackEntry ->
            val idKandang = backStackEntry.arguments?.getString("idKandang") ?: ""
            DetailKandangScreen(
                idKandang = idKandang,
                navigateBack = {
                    navController.navigate(DestinasiHomeKandang.route) {
                        popUpTo(DestinasiHomeKandang.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateKandang.route}/$idKandang")
                }
            )
        }

        composable(
            route = "${DestinasiUpdateKandang.route}/{idKandang}",
            arguments = listOf(navArgument("idKandang") { type = NavType.StringType })
        ) { backStackEntry ->
            val idKandang = backStackEntry.arguments?.getString("idKandang") ?: ""
            UpdateKandangScreen(
                idKandang = idKandang,
                navigateBack = {
                    navController.navigate(DestinasiHomeKandang.route) {
                        popUpTo(DestinasiHomeKandang.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // MONITORING
        composable(DestinasiHomeMonitoring.route) {
            HomeMonitoringScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertMonitoring.route)
                },
                onDetailClick = { idMonitoring ->
                    if (idMonitoring.isNotEmpty()) {
                        navController.navigate("${DestinasiDetailMonitoring.route}/$idMonitoring")
                    }
                },
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(DestinasiInsertMonitoring.route) {
            InsertMonitoringScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeMonitoring.route) {
                        popUpTo(DestinasiHomeMonitoring.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetailMonitoring.route}/{idMonitoring}",
            arguments = listOf(navArgument("idMonitoring") { type = NavType.StringType })
        ) { backStackEntry ->
            val idMonitoring = backStackEntry.arguments?.getString("idMonitoring") ?: ""
            DetailMonitoringScreen(
                idMonitoring = idMonitoring,
                navigateBack = {
                    navController.navigate(DestinasiHomeMonitoring.route) {
                        popUpTo(DestinasiHomeMonitoring.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateMonitoring.route}/$idMonitoring")
                }
            )
        }

        composable(
            route = "${DestinasiUpdateMonitoring.route}/{idMonitoring}",
            arguments = listOf(navArgument("idMonitoring") { type = NavType.StringType })
        ) { backStackEntry ->
            val idMonitoring = backStackEntry.arguments?.getString("idMonitoring") ?: ""
            UpdateMonitoringScreen(
                idMonitoring = idMonitoring,
                navigateBack = {
                    navController.navigate(DestinasiHomeMonitoring.route) {
                        popUpTo(DestinasiHomeMonitoring.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}
