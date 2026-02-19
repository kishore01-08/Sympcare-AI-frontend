package com.simats.sympcareai

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

// Data class for chat messages
data class VoiceMessage(val text: String, val isUser: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceAssistantScreen(
    onBackClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    selectedSymptoms: List<String> = emptyList()
) {
    val context = LocalContext.current
    var isListening by remember { mutableStateOf(false) }
    
    // Conversation State
    val messages = remember { mutableStateListOf<VoiceMessage>() }
    val listState = rememberLazyListState()

    // Questionnaire Logic
    val questions = remember {
        listOf(
            "How long have you been experiencing these symptoms?",
            "On a scale of 1 to 10, how severe is the pain?",
            "Do you have any known allergies?",
            "Are you currently taking any medications?",
            "Thank you. I have gathered your information. You can now click on Analyse to proceed."
        )
    }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    
    var speechRecognizer by remember { mutableStateOf<SpeechRecognizer?>(null) }
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    // Helper to speak and add message
    fun speakAndAddMessage(text: String) {
        if (!messages.any { !it.isUser && it.text == text }) { // Prevent duplicates on recomposition
             messages.add(VoiceMessage(text, isUser = false))
             speakText(tts, text)
        }
    }

    // Initialize TTS
    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
                // Speak first question after init
                if (messages.isEmpty()) {
                     val initialGreeting = "I see you have selected ${selectedSymptoms.joinToString(", ")}. ${questions[0]}"
                     speakAndAddMessage(initialGreeting)
                }
            }
        }
    }
    
    // Auto-scroll to bottom
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // File Picker Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            messages.add(VoiceMessage("Attached file: ${uri.lastPathSegment}", isUser = true))
             speakAndAddMessage("I've received your file. Analyzing it now.")
        }
    }

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                startListening(context, speechRecognizer) { isListening = true }
            } else {
                Toast.makeText(context, "Microphone permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    )



    fun analyzeSymptoms(symptoms: List<String>): String {
        return when {
            symptoms.contains("Fever") && symptoms.contains("Cough") -> "You might be experiencing a Viral Infection or Flu. Stay hydrated and rest."
            symptoms.contains("Headache") && symptoms.contains("Nausea") -> "This could be a Migraine. Avoid bright lights and noise."
            symptoms.contains("Chest Pain") -> "Chest pain can be serious. Please seek immediate medical attention."
            symptoms.contains("Stomach Ache") -> "It could be Indigestion or Gastritis. Avoid spicy food."
            symptoms.contains("Skin Rash") -> "This might be an Allergic Reaction. Avoid potential allergens."
            symptoms.isNotEmpty() -> "You have reported ${symptoms.joinToString(", ")}. This requires further clinical evaluation."
            else -> "I need more information to analyze your condition."
        }
    }

    fun processUserResponse(text: String) {
        if (text.isNotBlank()) {
            messages.add(VoiceMessage(text, isUser = true))
            
            // Advance to next question
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                 val nextQuestion = questions[currentQuestionIndex]
                 speakAndAddMessage(nextQuestion)
            } else {
                // End of flow - ANALYZE SYMPTOMS
                val analysis = analyzeSymptoms(selectedSymptoms)
                 speakAndAddMessage("Based on your symptoms: $analysis")
                 speakAndAddMessage("Please click Analyse to view your detailed report, or consult a doctor for a medical diagnosis.")
            }
        }
    }



    // Initialize Speech Recognizer
    DisposableEffect(Unit) {
        val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer = recognizer
        
        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                isListening = false
            }
            override fun onError(error: Int) {
                isListening = false
                // Optional: Handle error silently or show toast
            }
            override fun onResults(results: Bundle?) {
                isListening = false
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    processUserResponse(matches[0])
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
        
        recognizer.setRecognitionListener(listener)

        onDispose {
            recognizer.destroy()
            tts?.stop()
            tts?.shutdown()
        }
    }

    // Listening Popup Dialog
    if (isListening) {
        AlertDialog(
            onDismissRequest = {
                stopListening(speechRecognizer)
                isListening = false
            },
            icon = {
                Icon(Icons.Default.Mic, contentDescription = null, tint = Color(0xFF009688))
            },
            title = {
                Text(text = "Listening...")
            },
            text = {
                Text(
                    text = "Please speak now. Tap Stop to finish.",
                    modifier = Modifier.fillMaxWidth(),
                    
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        stopListening(speechRecognizer)
                        isListening = false
                    }
                ) {
                    Text("Stop")
                }
            },
            containerColor = Color.White
        )
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .imePadding(), // Ensure it moves with keyboard if needed
                contentAlignment = Alignment.Center
            ) {
                // Attachment Button
                IconButton(
                    onClick = { launcher.launch("*/*") },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(Color(0xFFF5F5F5), CircleShape) // Light Gray
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Attach File",
                        tint = Color(0xFF009688) // Teal
                    )
                }

                // Initial Mic Button (Compact)
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isListening) {
                        Text(
                            text = "Tap to Speak",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            if (isListening) {
                                stopListening(speechRecognizer)
                                isListening = false
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        },
                        modifier = Modifier
                            .size(64.dp) // Compact size
                            .background(Color.White, CircleShape)
                            .border(1.dp, Color(0xFF009688), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                            contentDescription = "Speak",
                            tint = if (isListening) Color.Red else Color(0xFF009688),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6E48AA), Color(0xFF9D50BB).copy(alpha = 0.1f))
                    )
                )
        ) {
            // App Bar
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Voice Assistant", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                        if (isListening) {
                            Text("Listening...", fontSize = 12.sp, color = Color(0xFF00E676))
                        } else {
                            Text("Online", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    Button(
                        onClick = { onNavigateTo(Screen.ReportAnalysis) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Analyse", color = Color.Black, fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Selected Symptoms Display
                if (selectedSymptoms.isNotEmpty()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                        Text(
                            text = "Selected Context:", 
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedSymptoms.forEach { symptom ->
                                Surface(
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                                ) {
                                    Text(
                                        text = symptom,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 12.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Dynamic Response List
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(messages) { message ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
                        ) {
                            Surface(
                                color = if (message.isUser) Color(0xFF009688) else Color.White,
                                shape = RoundedCornerShape(
                                    topStart = 16.dp, 
                                    topEnd = 16.dp,
                                    bottomStart = if (message.isUser) 16.dp else 4.dp,
                                    bottomEnd = if (message.isUser) 4.dp else 16.dp
                                ),
                                modifier = Modifier.widthIn(max = 280.dp)
                            ) {
                                Text(
                                    text = message.text,
                                    modifier = Modifier.padding(16.dp),
                                    color = if (message.isUser) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun startListening(
    context: Context,
    speechRecognizer: SpeechRecognizer?,
    onListeningStarted: () -> Unit
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }
    speechRecognizer?.startListening(intent)
    onListeningStarted()
}

private fun stopListening(speechRecognizer: SpeechRecognizer?) {
    speechRecognizer?.stopListening()
}

private fun speakText(tts: TextToSpeech?, text: String) {
    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
}
