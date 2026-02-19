package com.simats.sympcareai

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AIPatientAnalysisScreen(
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
                    .height(150.dp)
            ) {
                // Gradient Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFF6E6E), Color(0xFFFF9F43))
                            )
                        )
                ) {
                    // Back Button
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(top = 40.dp, start = 16.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Outlined.Lightbulb, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "AI Patient Analysis",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Doctor AI Assistant",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Possible Conditions Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                         Row(verticalAlignment = Alignment.CenterVertically) {
                             Surface(
                                 color = Color(0xFFFFEBEE),
                                 shape = RoundedCornerShape(8.dp),
                                 modifier = Modifier.size(30.dp)
                             ) {
                                 Box(contentAlignment = Alignment.Center) {
                                     Icon(Icons.Outlined.Description, contentDescription = null, tint = Color(0xFFD32F2F), modifier = Modifier.size(18.dp))
                                 }
                             }
                             Spacer(modifier = Modifier.width(12.dp))
                             Text(
                                 text = "Possible Conditions",
                                 fontWeight = FontWeight.Bold,
                                 fontSize = 16.sp,
                                 color = Color.Black
                             )
                         }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        ConditionItem(name = "Migraine", severity = "Moderate", color = Color(0xFFFFCDD2), textColor = Color(0xFFD32F2F))
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Transparent)
                        ConditionItem(name = "Dehydration", severity = "Mild", color = Color(0xFFFFE0B2), textColor = Color(0xFFF57C00))
                         Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Transparent)
                        ConditionItem(name = "Stress-related Fatigue", severity = "Mild", color = Color(0xFFFFE0B2), textColor = Color(0xFFF57C00))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Patient Priority Level Card
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
                        
                        // Circular Gauge
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
                
                Spacer(modifier = Modifier.height(20.dp))

                // Recommended Actions Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                     Column(modifier = Modifier.padding(16.dp)) {
                         Row(verticalAlignment = Alignment.CenterVertically) {
                             Surface(
                                 color = Color(0xFF00BFA5),
                                 shape = RoundedCornerShape(8.dp),
                                 modifier = Modifier.size(30.dp)
                             ) {
                                 Box(contentAlignment = Alignment.Center) {
                                     Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                 }
                             }
                             Spacer(modifier = Modifier.width(12.dp))
                             Text(
                                 text = "Recommended Actions",
                                 fontWeight = FontWeight.Bold,
                                 fontSize = 16.sp,
                                 color = Color.Black
                             )
                         }
                         
                         Spacer(modifier = Modifier.height(16.dp))
                         
                         RecommendationItem(icon = Icons.Outlined.WaterDrop, title = "Hydration", subtitle = "Drink 8 glasses of water daily")
                         Spacer(modifier = Modifier.height(8.dp))
                         RecommendationItem(icon = Icons.Outlined.Bedtime, title = "Rest", subtitle = "Get adequate sleep (7-8 hours)")
                         Spacer(modifier = Modifier.height(8.dp))
                         RecommendationItem(icon = Icons.Outlined.MedicalServices, title = "Consultation", subtitle = "See doctor if symptoms persist")
                     }
                }
            }
        }
    }
}

@Composable
fun ConditionItem(name: String, severity: String, color: Color, textColor: Color) {
    Surface(
        color = color.copy(alpha = 0.3f), // Light background
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(6.dp).background(Color(0xFFE57373), CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = name, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Surface(
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, textColor.copy(alpha = 0.2f))
            ) {
                Text(
                    text = severity,
                    color = textColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RecommendationItem(icon: ImageVector, title: String, subtitle: String) {
    Surface(
        color = Color(0xFFE0F2F1), // Light Teal
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
             Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(36.dp)
            ) {
                 Box(contentAlignment = Alignment.Center) {
                     Icon(icon, contentDescription = null, tint = Color(0xFF009688) /* Teal */, modifier = Modifier.size(20.dp))
                 }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                Text(text = subtitle, color = Color.Gray, fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun CircularPriorityGauge(
    value: Float, // 0.0 to 1.0
    color: Color,
    size: Dp = 100.dp,
    strokeWidth: Dp = 8.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        // Background Arc
        drawArc(
            color = color.copy(alpha = 0.2f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
        // Foreground Arc
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360 * value,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
