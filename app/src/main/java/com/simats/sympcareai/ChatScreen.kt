package com.simats.sympcareai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ChatScreen(
    onBackClick: () -> Unit,
    onAttachmentClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    onFinishClick: () -> Unit,
    isReadOnly: Boolean = false,
    selectedSymptoms: List<String> = emptyList()
) {
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    
    // Conversation State
    val messages = remember { mutableStateListOf<ChatMessage>() }
    val listState = rememberLazyListState()
    
    // Questionnaire Logic (Mirrors Voice Assistant for consistency)
    val questions = remember {
        listOf(
            "How long have you been experiencing these symptoms?",
            "On a scale of 1 to 10, how severe is the pain?",
            "Do you have any known allergies?",
            "Are you currently taking any medications?",
            "Thank you. I have gathered your information. You can now click 'Finish' to proceed to analysis."
        )
    }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }

    // Helper to analyze symptoms (Local version for Chat)
    fun analyzeSymptoms(symptoms: List<String>): String {
        return when {
            symptoms.contains("Fever") && symptoms.contains("Cough") -> "Potential Viral Infection or Flu. Stay hydrated and rest."
            symptoms.contains("Headache") && symptoms.contains("Nausea") -> "Possible Migraine or Dehydration. Avoid bright lights."
            symptoms.contains("Chest Pain") -> "Chest pain requires immediate attention. Please consult a doctor."
            symptoms.contains("Stomach Ache") -> "Could be Indigestion or Gastritis. Watch your diet."
            symptoms.contains("Skin Rash") -> "Possible Allergic Reaction. Avoid allergens."
            symptoms.isNotEmpty() -> "Symptoms reported: ${symptoms.joinToString(", ")}. Further evaluation recommended."
            else -> "Need more info for analysis."
        }
    }

    // Initial Greeting
    LaunchedEffect(Unit) {
        if (messages.isEmpty() && !isReadOnly) {
            val symptomText = if (selectedSymptoms.isNotEmpty()) " for ${selectedSymptoms.joinToString(", ")}" else ""
            messages.add(ChatMessage("Hello! I'm here to help with your symptoms$symptomText. ${questions[0]}", isUser = false))
        }
    }

    // Auto-scroll
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    fun sendMessage() {
        if (message.isNotBlank()) {
            val userMsg = message
            messages.add(ChatMessage(userMsg, isUser = true))
            message = ""

            // AI Response Logic
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                val nextQ = questions[currentQuestionIndex]
                // Simulate delay could go here with coroutine, but for UI responsiveness direct add is fine/snappy
                messages.add(ChatMessage(nextQ, isUser = false))
            } else {
                // Analysis
                val analysis = analyzeSymptoms(selectedSymptoms)
                messages.add(ChatMessage("Based on your answers and selected symptoms: $analysis", isUser = false))
                messages.add(ChatMessage("Please click 'Finish' to generate your full report.", isUser = false))
            }
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                currentScreen = Screen.Chat,
                onNavigateTo = onNavigateTo
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFF5F5F5))) {
        // App Bar
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SympCareAI", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    Text("Health AI Online", fontSize = 12.sp, color = Color(0xFF009688))
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.DarkGray)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
            actions = {
                if (!isReadOnly) {
                    Button(
                        onClick = onFinishClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.padding(end = 8.dp).height(36.dp)
                    ) {
                        Text("Finish", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        )

        // Chat Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selected Symptoms Display
            if (selectedSymptoms.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Selected Symptoms:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedSymptoms.forEach { symptom ->
                            Surface(
                                color = Color(0xFFE0F2F1),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Color(0xFF009688))
                            ) {
                                Text(
                                    text = symptom,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    fontSize = 12.sp,
                                    color = Color(0xFF009688)
                                )
                            }
                        }
                    }
                }
            }

            // Reference to List State for auto-scroll
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(messages) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(), 
                        horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                    ) {
                        ChatBubble(text = msg.text, isUser = msg.isUser)
                    }
                }
            }
        }


        // Input Area - Only show if not read-only
        if (!isReadOnly) {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text("Describe your symptoms...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = Color.Gray)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Upload File") },
                                onClick = {
                                    expanded = false
                                    onAttachmentClick()
                                },
                                 leadingIcon = {
                                    Icon(Icons.Default.CloudUpload, contentDescription = null)
                                }
                            )
                        }
                    }
                    IconButton(onClick = { sendMessage() }) {
                       Icon(Icons.Default.Send, contentDescription = "Send", tint = Color(0xFF009688))
                    }
                }
            }
        }
    }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    Surface(
        color = if (isUser) Color(0xFF009688) else Color.White,
        shape = if (isUser) RoundedCornerShape(16.dp).copy(bottomEnd = CornerSize(0.dp))
                else RoundedCornerShape(16.dp).copy(bottomStart = CornerSize(0.dp)),
        shadowElevation = 2.dp,
        modifier = Modifier.widthIn(max = 280.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            color = if (isUser) Color.White else Color.Black,
            fontSize = 14.sp
        )
    }
}
