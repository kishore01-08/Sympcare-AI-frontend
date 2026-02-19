package com.simats.sympcareai

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HealthReportsScreen(
    onBackClick: () -> Unit,
    onReportClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = {
            AppBottomNavigationBar(
                currentScreen = Screen.PatientHome, // Heuristic: Reports is part of Home/History flow
                onNavigateTo = onNavigateTo
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEF6C00)) // Orange
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                 Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                     Box(modifier = Modifier.fillMaxWidth()) {
                         IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                             Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                         }
                         Surface(
                             shape = CircleShape,
                             color = Color.White,
                             modifier = Modifier.size(56.dp).align(Alignment.Center),
                             shadowElevation = 4.dp
                         ) {
                             Box(contentAlignment = Alignment.Center) {
                                  Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFFEF6C00), modifier = Modifier.size(28.dp))
                             }
                         }
                     }
                     Spacer(modifier = Modifier.height(16.dp))
                     Text("Health Reports", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                     Text("Your consultation history", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
                 }
            }

            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                
                // Report 1
                ReportCard(
                    title = "Headache & Fatigue Consultation",
                    date = "Jan 27, 2026 • 15 mins",
                    doctorName = "AI Assistant",
                    type = "Voice Chat",
                    typeIcon = Icons.Default.Mic,
                    tags = listOf("Headache", "Fatigue", "Stress"),
                    status = "COMPLETED",
                    statusColor = Color(0xFF4CAF50), // Green
                    onClick = onReportClick
                )

                // Report 2
                ReportCard(
                    title = "Cold & Flu Symptoms",
                    date = "Jan 25, 2026 • 20 mins",
                    doctorName = "AI Assistant",
                    type = "Text Chat",
                    typeIcon = Icons.Default.Textsms,
                    tags = listOf("Cold", "Fever", "Cough"),
                    status = "COMPLETED",
                    statusColor = Color(0xFF4CAF50),
                    onClick = onReportClick
                )

                // Report 3
                ReportCard(
                    title = "Annual Health Check-up",
                    date = "Jan 20, 2026 • 45 mins",
                    doctorName = "Dr. Sarah Johnson",
                    type = "Video Call",
                    typeIcon = Icons.Default.Videocam,
                    tags = listOf("General Check-up", "Blood Work"),
                    status = "COMPLETED",
                    statusColor = Color(0xFF4CAF50),
                    onClick = onReportClick
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReportCard(
    title: String,
    date: String,
    doctorName: String,
    type: String,
    typeIcon: ImageVector,
    tags: List<String>,
    status: String,
    statusColor: Color,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                }
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(50),
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = status,
                        Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            
            // Details
            Text(date, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray) // Person icon heuristic
                Spacer(modifier = Modifier.width(4.dp))
                Text(doctorName, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(typeIcon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(type, fontSize = 12.sp, color = Color.Gray)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(12.dp))

            // Tags
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    Surface(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBDEFB))
                    ) {
                        Text(
                            text = tag,
                            Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            color = Color(0xFF1976D2),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Action
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tap to view full report", color = Color(0xFF2196F3), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color(0xFF2196F3), modifier = Modifier.size(16.dp))
            }
        }
    }
}
