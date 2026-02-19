package com.simats.sympcareai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataPrivacyScreen(
    onBackClick: () -> Unit
) {
    // Permission States (Mock)
    var cameraPermission by remember { mutableStateOf(true) }
    var micPermission by remember { mutableStateOf(true) }
    var storagePermission by remember { mutableStateOf(false) }
    var locationPermission by remember { mutableStateOf(false) }

    // Privacy Settings
    var shareData by remember { mutableStateOf(true) }
    var saveHistory by remember { mutableStateOf(true) }
    var personalizedAds by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Data & Privacy", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "App Permissions",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    PrivacyToggleItem(
                        icon = Icons.Default.CameraAlt,
                        title = "Camera",
                        description = "Allow app to access camera for photos and scanning.",
                        checked = cameraPermission,
                        onCheckedChange = { cameraPermission = it }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))
                    PrivacyToggleItem(
                        icon = Icons.Default.Mic,
                        title = "Microphone",
                        description = "Allow app to access microphone for voice commands.",
                        checked = micPermission,
                        onCheckedChange = { micPermission = it }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))
                    PrivacyToggleItem(
                        icon = Icons.Default.Folder,
                        title = "Storage",
                        description = "Allow app to access files for uploading reports.",
                        checked = storagePermission,
                        onCheckedChange = { storagePermission = it }
                    )
                     Divider(color = Color.LightGray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))
                    PrivacyToggleItem(
                        icon = Icons.Default.LocationOn,
                        title = "Location",
                        description = "Allow app to access location for nearby services.",
                        checked = locationPermission,
                        onCheckedChange = { locationPermission = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Privacy Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    PrivacyToggleItem(
                        icon = Icons.Outlined.Analytics, // Using outlined for settings
                        title = "Share Usage Data",
                        description = "Help us improve Sympcare AI by sharing anonymous usage data.",
                        checked = shareData,
                        onCheckedChange = { shareData = it }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))
                    PrivacyToggleItem(
                        icon = Icons.Outlined.History,
                        title = "Save Chat History",
                        description = "Keep a record of your AI consultations for future reference.",
                        checked = saveHistory,
                        onCheckedChange = { saveHistory = it }
                    )
                     Divider(color = Color.LightGray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))
                    PrivacyToggleItem(
                        icon = Icons.Outlined.AdsClick,
                        title = "Personalized Content",
                        description = "Allow personalized health tips and recommendations.",
                        checked = personalizedAds,
                        onCheckedChange = { personalizedAds = it }
                    )
                }
            }
            

        }
    }
}

@Composable
fun PrivacyToggleItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF009688), // Teal
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF009688),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}
