package com.example.lastchapter.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lastchapter.R
import com.example.lastchapter.ui.navigation.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "item_update"
    override val titleRes = "Update Petugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashHomeViews(
    onPetugasClick: () -> Unit,
    onHewanClick: () -> Unit,
    onKandangClick: () -> Unit,
    onMonitoringClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for animation control
    var isVisible by remember { mutableStateOf(false) }

    // Trigger animation when the view is loaded
    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Create scroll behavior
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Selamat Datang",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                scrollBehavior = scrollBehavior, // Attach scroll behavior
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF4CAF50), // Green for fresh look
                    scrolledContainerColor = Color(0xFF388E3C) // Darker green when scrolled
                )
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) // Enable nested scroll
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF1F1F1)) // Soft light background color
                .padding(innerPadding) // Avoid overlapping content with top bar
                .verticalScroll(rememberScrollState()) // Enables scrolling
        ) {
            // Main content: Column centered vertically and horizontally
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp) // Larger padding for a cleaner layout
                    .weight(1f), // Makes this Column take up the available space
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Ensures buttons are centered vertically
            ) {
                // Add logo image above the buttons
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Use the logo (ensure umy.png is in res/drawable)
                    contentDescription = "UMY Logo",
                    modifier = Modifier
                        .size(350.dp) // Larger size for the logo
                        .padding(bottom = 64.dp) // Add more padding below the logo to space it further from buttons
                )

                // Buttons with animations
                AnimatedButton(text = "Petugas", icon = Icons.Filled.AccountBox, onClick = onPetugasClick)
                Spacer(modifier = Modifier.height(16.dp)) // Space between buttons
                AnimatedButton(text = "Hewan", icon = Icons.Filled.Create, onClick = onHewanClick)
                Spacer(modifier = Modifier.height(16.dp)) // Space between buttons
                AnimatedButton(text = "Kandang", icon = Icons.Filled.Home, onClick = onKandangClick)
                Spacer(modifier = Modifier.height(16.dp)) // Space between buttons
                AnimatedButton(text = "Monitoring", icon = Icons.Filled.DateRange, onClick = onMonitoringClick)
            }
        }
    }
}

@Composable
fun AnimatedButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    // Button with smooth animation and modern design
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4CAF50), // Green color to match the top bar
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth(0.8f) // Making button wider for better click area
            .height(80.dp) // Taller button
            .clickable { isPressed = !isPressed }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center, // Centering icon and text
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon with smooth scaling animation
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(40.dp), // Medium icon size
                tint = Color.White
            )
            // Text with animation on press
            Text(
                text = text,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .animateScale(isPressed) // Adding animation to text
            )
        }
    }
}

@Composable
fun Modifier.animateScale(isPressed: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300) // Smooth animation effect
    )
    return this.then(Modifier.scale(scale))
}
