package com.simats.sympcareai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DoctorPatientDetailsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFF5F5F5)
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
                    .height(130.dp)
            ) {
                // Gradient Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF6A5ACD), Color(0xFF9683EC))
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackClick
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(
                                text = "John Doe",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "ID: PAT-10234",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Surface(
                            color = Color(0xFFE0F2F1), // Light Teal
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "Active",
                                color = Color(0xFF009688), // Teal
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .offset(y = (-30).dp) // Overlap
            ) {
                // Patient Summary Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF00BFA5),
                            modifier = Modifier.size(60.dp)
                        ) {
                            // Avatar logic or placeholder
                            Box(contentAlignment = Alignment.Center) {
                                // Using Emoji/Icon as per design
                                Text("👨", fontSize = 24.sp) 
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "John Doe",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Last visit: Jan 25, 2026",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Row(
                            Modifier.fillMaxWidth(), 
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PatientInfoTile(label = "Age", value = "29")
                            PatientInfoTile(label = "Gender", value = "Male")
                            PatientInfoTile(label = "Blood", value = "O+")
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        Divider(color = Color.LightGray.copy(alpha = 0.2f))
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                             Text(text = "Primary Symptoms", fontSize = 12.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SymptomTag(text = "Headache", color = Color(0xFFFFCDD2), textColor = Color(0xFFD32F2F))
                            SymptomTag(text = "Fatigue", color = Color(0xFFFFCCBC), textColor = Color(0xFFD84315))
                            SymptomTag(text = "Dizziness", color = Color(0xFFFFE0B2), textColor = Color(0xFFE65100))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))

                // Personal Health Report Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                         Row(verticalAlignment = Alignment.Top) {
                             Surface(
                                 color = Color(0xFF448AFF), // Blue
                                 shape = RoundedCornerShape(12.dp),
                                 modifier = Modifier.size(50.dp)
                             ) {
                                 Box(contentAlignment = Alignment.Center) {
                                     Icon(Icons.Outlined.Description, contentDescription = null, tint = Color.White)
                                 }
                             }
                             Spacer(modifier = Modifier.width(16.dp))
                             Column {
                                 Text(
                                     text = "Personal Health Report",
                                     fontWeight = FontWeight.Bold,
                                     fontSize = 16.sp,
                                     color = Color.Black
                                 )
                                 Spacer(modifier = Modifier.height(4.dp))
                                  Text(
                                     text = "Medical reports uploaded by the patient in their profile",
                                     color = Color.Gray,
                                     fontSize = 12.sp,
                                     lineHeight = 16.sp
                                 )
                             }
                         }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        Divider(color = Color.LightGray.copy(alpha = 0.2f))
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                             Row(verticalAlignment = Alignment.CenterVertically) {
                                 Icon(Icons.Outlined.Description, contentDescription = null, tint = Color(0xFF448AFF), modifier = Modifier.size(16.dp))
                                 Spacer(modifier = Modifier.width(4.dp))
                                 Text("3 files uploaded", color = Color(0xFF448AFF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                             }
                             
                             Row(
                                 verticalAlignment = Alignment.CenterVertically, 
                                 modifier = Modifier.clickable { /* TODO: View Reports */ }
                             ) {
                                 Text("View Report", color = Color(0xFF448AFF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                 Spacer(modifier = Modifier.width(4.dp))
                                 Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color(0xFF448AFF), modifier = Modifier.size(16.dp))
                             }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))

                // AI Analysis Report Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = Color(0x407B61FF),
                            spotColor = Color(0x407B61FF)
                        )
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFF7B61FF), // Vibrant Purple
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.size(56.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Outlined.Lightbulb,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "AI Analysis Report",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFF1E1E2E)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "AI-generated report based on the patient's chat and symptoms",
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = Color(0xFFF0F0F5))
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color(0xFF7B61FF), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Latest analysis: Jan 27,",
                                        color = Color(0xFF7B61FF),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "2026",
                                        color = Color(0xFF7B61FF),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { /* TODO: View AI Report */ }
                                    .background(Color.Transparent)
                            ) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "View AI",
                                        color = Color(0xFF7B61FF),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "Report",
                                        color = Color(0xFF7B61FF),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color(0xFF7B61FF),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Patient Priority Detail Card (Reuse if needed, or simple view as shown in previous screen? 
                // The screenshot shows Priority Level again here at bottom)
                Card(
                     colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                      Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PATIENT PRIORITY LEVEL",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Circular Gauge Reuse
                        Box(contentAlignment = Alignment.Center) {
                            CircularPriorityGauge(value = 0.6f, color = Color(0xFFD32F2F)) // 60%
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("LEVEL", fontSize = 10.sp, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                                Text("3", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F))
                            }
                        }
                        
                         Spacer(modifier = Modifier.height(20.dp))
                        
                        Surface(
                            color = Color(0xFFFFEBEE),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                             Text(
                                 text = "Moderate Priority",
                                 color = Color(0xFFD32F2F),
                                 fontSize = 12.sp,
                                 fontWeight = FontWeight.Bold,
                                 modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                             )
                        }
                        
                         Spacer(modifier = Modifier.height(12.dp))
                        
                         Text(
                             text = "Moderate Priority – Needs consultation within 24 hours",
                             color = Color.Gray,
                             fontSize = 11.sp,
                             textAlign = TextAlign.Center
                         )
                    }
                }
                
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun PatientInfoTile(label: String, value: String) {
    Surface(
        color = Color(0xFFFAFAFA),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(100.dp)
            .height(70.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(label, color = Color.Gray, fontSize = 12.sp)
            Text(value, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun SymptomTag(text: String, color: Color, textColor: Color) {
    Surface(
        color = color,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
