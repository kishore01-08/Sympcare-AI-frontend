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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HealthAssessmentScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("Good") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
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
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF2196F3), Color(0xFF009688))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                        Spacer(modifier = Modifier.weight(1f))
                         Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Question 1 of 5",
                                color = Color.White,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Health Assessment",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Evening Health Review",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            
            // Question Card - Overlapping Header
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-40).dp)
                    .padding(horizontal = 24.dp)
            ) {
                Card(
                     colors = CardDefaults.cardColors(containerColor = Color.White),
                     shape = RoundedCornerShape(24.dp),
                     elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                     modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Question Icon
                         Surface(
                            color = Color(0xFF2196F3),
                            shape = CircleShape,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("?", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "How would you rate your sleep quality over the past week?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Options
                        AssessmentOption(
                            emoji = "😴",
                            label = "Poor",
                            selected = selectedOption == "Poor",
                            onSelect = { selectedOption = "Poor" }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                         AssessmentOption(
                            emoji = "😕",
                            label = "Fair",
                            selected = selectedOption == "Fair",
                            onSelect = { selectedOption = "Fair" }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                         AssessmentOption(
                            emoji = "😊",
                            label = "Good",
                            selected = selectedOption == "Good",
                            onSelect = { selectedOption = "Good" }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                         AssessmentOption(
                            emoji = "😄",
                            label = "Very Good",
                            selected = selectedOption == "Very Good",
                            onSelect = { selectedOption = "Very Good" }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                         AssessmentOption(
                            emoji = "🌟",
                            label = "Excellent",
                            selected = selectedOption == "Excellent",
                            onSelect = { selectedOption = "Excellent" }
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Button(
                            onClick = onNextClick,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)), // Light Gray per design screenshot
                            shape = RoundedCornerShape(12.dp)
                        ) {
                             Text("Next", color = Color.Gray)
                             Spacer(modifier = Modifier.width(8.dp))
                             Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AssessmentOption(
    emoji: String,
    label: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (selected) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF2196F3)) else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onSelect)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2196F3))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
