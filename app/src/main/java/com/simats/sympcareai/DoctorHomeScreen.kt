package com.simats.sympcareai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DoctorHomeScreen(
    onPatientPortalClick: () -> Unit,
    onChatClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onPatientsTodayClick: () -> Unit,
    onCompletedPatientsClick: () -> Unit,
    patientsTodayCount: Int = 24, // Default value for preview/fallback
    completedPatientsCount: Int = 18 // Default value
) {
    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                currentScreen = Screen.DoctorHome,
                onNavigateTo = onNavigateTo,
                isDoctor = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section with Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF6A5ACD), Color(0xFF9683EC))
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { onProfileClick() },
                            shape = CircleShape,
                            color = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color(0xFF6A5ACD),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "WELCOME BACK",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Dr. Sarah Johnson",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        // Notification Icon
                        IconButton(onClick = onNotificationClick) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Status Indicator
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(Color(0xFF4CAF50), CircleShape) // Green dot
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Available",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Patients Today Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp)
                            .clickable { onPatientsTodayClick() },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1C338)) // keep as is or change? User said "all screens of doctor". Let's check if this needs to change. 
                        // The user said "profile whole color grading of orange shade to 0xFF6A5ACD". 
                        // The "Patients Today" card is Yellow/Orange. I should probably change it to a complementary purple or keep it distinct.
                        // Let's keep it distinct for now as it's a specific status card, unless explicitly asked.
                        // BUT, the request said "all screens of doctor and login of doctor".
                        // Let's change the Quick Action icons though.
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Groups, contentDescription = null, tint = Color.White)
                                }
                            }
                            Column {
                                Text(
                                    text = "$patientsTodayCount",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Pending",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    // Completed Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp)
                            .clickable { onCompletedPatientsClick() },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF00BFA5)) // Teal/Green
                    ) {
                         Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                                }
                            }
                            Column {
                                Text(
                                    text = "$completedPatientsCount",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Completed",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Quick Actions Title
                Text(
                    text = "QUICK ACTIONS",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Patient Portal Action
                    QuickActionCard(
                        icon = Icons.Default.Person,
                        label = "Patient Portal",
                        iconColor = Color(0xFF6A5ACD), // SlateBlue
                        onClick = onPatientPortalClick,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Start AI Chat Action
                     QuickActionCard(
                        icon = Icons.Default.Chat,
                        label = "Start AI Chat",
                        iconColor = Color(0xFF9683EC), // Light Purple
                        onClick = onChatClick,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Active Patients Title
                Text(
                     text = "ACTIVE PATIENTS",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Active Patients List
                ActivePatientCard(
                    name = "John Doe",
                    symptom = "Headache",
                    time = "Now",
                    status = "Online",
                    statusColor = Color(0xFFE0F2F1),
                    statusTextColor = Color(0xFF009688),
                    initial = "J",
                    initialBg = Color(0xFF00BFA5)
                )
                Spacer(modifier = Modifier.height(12.dp))
                ActivePatientCard(
                    name = "Priya Sharma",
                    symptom = "Fever",
                    time = "10m",
                    status = "Completed",
                    statusColor = Color(0xFFE8EAF6),
                    statusTextColor = Color(0xFF3F51B5),
                    initial = "P",
                    initialBg = Color(0xFF448AFF)
                )
                Spacer(modifier = Modifier.height(12.dp))
                ActivePatientCard(
                    name = "Alex Chen",
                    symptom = "Wellness",
                    time = "15m",
                    status = "Waiting",
                    statusColor = Color(0xFFFFF8E1),
                    statusTextColor = Color(0xFFFF8F00),
                    initial = "A",
                    initialBg = Color(0xFFFFB300)
                )
                
                 Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


