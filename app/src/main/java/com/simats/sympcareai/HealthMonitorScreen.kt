package com.simats.sympcareai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.activity.compose.BackHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthMonitorScreen(
    onBackClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit
) {
    BackHandler(onBack = onBackClick)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Stats Cards Row removed
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header Item
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFF2196F3), Color(0xFF009688))
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = onBackClick,
                                    modifier = Modifier
                                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                        .size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }
                                
                                IconButton(
                                     onClick = { /* Check menu */ },
                                     modifier = Modifier
                                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                        .size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "More",
                                        tint = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Health Monitor",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Track your wellness journey",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // Previous Sessions Title
                item {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "Previous Sessions",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Session Items
                item {
                    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        HealthSessionCard(
                            title = "Morning Wellness Check",
                            date = "Feb 5, 2026",
                            time = "15 min",
                            status = "Improved",
                            statusColor = Color(0xFFE0F2F1), // Light Teal
                            statusTextColor = Color(0xFF009688),
                            onClick = { onNavigateTo(Screen.HealthAssessment) } // Logic: Review or Retake
                        )
                    }
                }
                item {
                    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        HealthSessionCard(
                            title = "Evening Health Review",
                            date = "Feb 3, 2026",
                            time = "12 min",
                            status = "Improved",
                            statusColor = Color(0xFFE0F2F1),
                            statusTextColor = Color(0xFF009688),
                            onClick = { onNavigateTo(Screen.HealthAssessment) }
                        )
                    }
                }
                item {
                    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        HealthSessionCard(
                            title = "Weekly Mental Health",
                            date = "Feb 1, 2026",
                            time = "20 min",
                            status = "Neutral",
                            statusColor = Color(0xFFFFF3E0), // Light Orange
                            statusTextColor = Color(0xFFFF9800),
                            onClick = { onNavigateTo(Screen.HealthAssessment) }
                        )
                    }
                }
                
                // Bottom Padding
                item {
                     Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}



@Composable
fun HealthSessionCard(
    title: String,
    date: String,
    time: String,
    status: String,
    statusColor: Color,
    statusTextColor: Color,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Surface(
                    color = statusColor,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(statusTextColor, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = status,
                            fontSize = 12.sp,
                            color = statusTextColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "📅 $date",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "🕒 $time",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Take follow-up assessment",
                    color = Color(0xFF2196F3),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
