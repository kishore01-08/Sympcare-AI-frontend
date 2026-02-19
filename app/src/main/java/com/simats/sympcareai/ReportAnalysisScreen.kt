package com.simats.sympcareai

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportAnalysisScreen(
    onBackClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    onNextClick: () -> Unit,
    selectedSymptoms: List<String> = emptyList()
) {
    // Dynamic Logic Breakdown
    val analysisText = remember(selectedSymptoms) {
        when {
            selectedSymptoms.contains("Fever") && selectedSymptoms.contains("Cough") -> 
                "Based on the reported symptoms of Fever and Cough, the analysis suggests a possible viral infection or influenza. The body is showing signs of fighting an infection."
            selectedSymptoms.contains("Headache") && selectedSymptoms.contains("Nausea") ->
                "The combination of Headache and Nausea typically points towards a Migraine or severe dehydration. Stress factors might also be contributing."
             selectedSymptoms.contains("Chest Pain") ->
                "Chest pain is a critical symptom. While it could be due to acidity or muscle strain, it requires immediate medical attention to rule out cardiac issues."
             selectedSymptoms.contains("Stomach Ache") ->
                 "Stomach ache can be associated with dietary issues, gastritis, or indigestion."
             selectedSymptoms.contains("Skin Rash") ->
                 "Skin rash suggests a potential allergic reaction or dermatological condition."
            selectedSymptoms.isNotEmpty() -> 
                "You have reported ${selectedSymptoms.joinToString(", ")}. The analysis suggests monitoring these symptoms closely as they might indicate an underlying condition requiring medical consultation."
            else -> "No specific symptoms were provided for analysis. General wellness advice is recommended."
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Health Report Analysis", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF00C9B9))
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                currentScreen = Screen.PatientHome, // heuristic, keeps Home active or can add Report enum
                onNavigateTo = onNavigateTo
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Info
            Text("AI-generated insights based on your data", color = Color(0xFF009688), fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

            // Analysis Based On
            SectionCard(title = "Analysis Based On", color = Color(0xFF009688)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ChatBubble, contentDescription = null, tint = Color(0xFF673AB7))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Chat Symptoms", fontWeight = FontWeight.SemiBold)
                    }
                    // Dynamic Chips
                    if (selectedSymptoms.isNotEmpty()) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            selectedSymptoms.take(3).forEach { symptom ->
                                SuggestionChip(
                                    onClick = {}, 
                                    label = { Text(symptom) }, 
                                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFEDE7F6))
                                )
                            }
                        }
                    } else {
                         Text("No symptoms selected", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 32.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFF1976D2))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Uploaded Files", fontWeight = FontWeight.SemiBold)
                    }
                     Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SuggestionChip(onClick = {}, label = { Text("None") }, colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFE3F2FD)))
                    }
                }
            }

            // Symptom Analysis
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("| Symptom Analysis", fontWeight = FontWeight.Bold, color = Color(0xFF4A148C))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = analysisText,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                }
            }

            // Possible Health Conditions (Dynamic)
            SectionCard(title = "Possible Health Conditions", color = Color(0xFFF44336)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (selectedSymptoms.contains("Fever")) {
                        ConditionItem(title = "Viral Infection", risk = "Moderate Risk", color = Color(0xFFFF9800))
                    } else if (selectedSymptoms.contains("Headache")) {
                         ConditionItem(title = "Migraine", risk = "Low Risk", color = Color(0xFF4CAF50))
                         ConditionItem(title = "Dehydration", risk = "Moderate Risk", color = Color(0xFFFF9800))
                    } else if (selectedSymptoms.contains("Chest Pain")) {
                         ConditionItem(title = "Cardiac Issue", risk = "High Risk", color = Color(0xFFF44336))
                         ConditionItem(title = "Muscle Strain", risk = "Low Risk", color = Color(0xFF4CAF50))
                    } else {
                         ConditionItem(title = "General Fatigue", risk = "Low Risk", color = Color(0xFF4CAF50))
                    }
                }
            }

            // AI Recommendations (Static for now, but context-aware)
            SectionCard(title = "AI Recommendations", color = Color(0xFF009688)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                     RecommendationItem("Maintain proper hydration levels.")
                     RecommendationItem("Ensure 7-8 hours of quality sleep.")
                     if (selectedSymptoms.contains("Fever")) {
                         RecommendationItem("Monitor body temperature every 4 hours.")
                     }
                     RecommendationItem("Consult a doctor if symptoms persist/worsen.")
                }
            }

            // Recommended Action Plan
            SectionCard(title = "Recommended Action Plan", color = Color(0xFF1976D2)) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionPlanItem(number = "1", title = "Lifestyle", subtitle = "Rest and Hydration", color = Color(0xFF2196F3))
                    ActionPlanItem(number = "2", title = "Monitoring", subtitle = "Track symptom progression", color = Color(0xFFFFC107))
                    ActionPlanItem(number = "3", title = "Medical", subtitle = "Tele-consultation if needed", color = Color(0xFF4CAF50))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Button(
                onClick = {}, // TODO: Generate PDF
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C9B9)),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate Detailed Report (PDF)")
            }
            
            OutlinedButton(
                onClick = {}, // TODO: Share
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
            ) {
                 Icon(Icons.Default.Share, contentDescription = null, tint = Color.Gray)
                 Spacer(modifier = Modifier.width(8.dp))
                 Text("Share Report", color = Color.Gray)
            }
            
             Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF)),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SectionCard(title: String, color: Color, content: @Composable () -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
             Box(modifier = Modifier.width(4.dp).height(16.dp).background(color, RoundedCornerShape(2.dp)))
             Spacer(modifier = Modifier.width(8.dp))
             Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun ConditionItem(title: String, risk: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth() // Ensure full width in column
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
             Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
             Spacer(modifier = Modifier.height(8.dp))
             Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
             Text(risk, fontSize = 12.sp, color = color)
        }
    }
}

@Composable
fun RecommendationItem(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00C9B9), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.8f))
    }
}

@Composable
fun ActionPlanItem(number: String, title: String, subtitle: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            color = color,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(number, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
