package com.simats.sympcareai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

data class DoctorPatient(
    val id: String,
    val name: String,
    val time: String,
    val status: String,
    val statusColor: Color,
    val statusTextColor: Color,
    val initial: String,
    val initialBg: Color
)

@Composable
fun DoctorPatientsScreen(
    onNavigateTo: (Screen) -> Unit,
    onBackClick: () -> Unit,
    onPatientComplete: (DoctorPatient) -> Unit,
    patients: List<DoctorPatient>,
    filter: String = "Total" // "Total", "Pending", "Completed"
) {
    val filteredPatients = remember(patients, filter) {
        when (filter) {
            "Pending" -> patients.filter { it.status != "Completed" }
            "Completed" -> patients.filter { it.status == "Completed" }
            else -> patients // Total
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                currentScreen = Screen.DoctorPatients,
                onNavigateTo = onNavigateTo,
                isDoctor = true
            )
        },
        containerColor = Color(0xFFF5F5F5) // Light gray background
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
                    .height(260.dp) // Height for header
            ) {
                // Gradient Background
                Box(
                     modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF6A5ACD), Color(0xFF9683EC))
                            )
                        )
                ) {
                    // Back Button
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(60.dp),
                            shadowElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Groups,
                                    contentDescription = "Patients",
                                    tint = Color(0xFF6A5ACD),
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$filter Patients",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "TOTAL PATIENTS: ${filteredPatients.size}",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            // Patients List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .offset(y = (-40).dp) // Overlap with header
            ) {
                filteredPatients.forEach { patient ->
                    ActivePatientCard(
                        name = patient.name,
                        time = patient.time,
                        status = patient.status,
                        statusColor = patient.statusColor,
                        statusTextColor = patient.statusTextColor,
                        initial = patient.initial,
                        initialBg = patient.initialBg,
                        onMarkComplete = {
                            onPatientComplete(patient)
                        }
                    ) { onNavigateTo(Screen.DoctorPatientDetails(patient.id)) }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            
                // Add more dummy data if needed
            }
        }
    }
}
