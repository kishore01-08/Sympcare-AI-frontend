package com.simats.sympcareai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Lock
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.shadow
import com.simats.sympcareai.ui.rememberBitmapFromUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDoctorProfileScreen(
    onBackClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("Dr. Sarah Johnson") }
    var age by remember { mutableStateOf("34") }
    var gender by remember { mutableStateOf("Female") }
    var specialization by remember { mutableStateOf("General Physician") }
    var expandedGender by remember { mutableStateOf(false) }
    var expandedSpec by remember { mutableStateOf(false) }

    val genderOptions = listOf("Male", "Female", "Other")
    val specializationOptions = listOf("General Physician", "Cardiologist", "Neurologist", "Pediatrician")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Smaller header for edit screen
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
                    // Toolbar Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }

                        Button(
                            onClick = { 
                                // TODO: Save Logic
                                onBackClick() 
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF6A5ACD)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Save", fontWeight = FontWeight.Bold)
                        }
                    }

                    // Title
                    Text(
                        text = "Edit Profile",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 16.dp)
                    )
                }

                // Profile Avatar
                val imageUri = remember { mutableStateOf<Uri?>(null) }
                val bitmap = rememberBitmapFromUri(imageUri.value)
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia()
                ) { uri: Uri? ->
                    imageUri.value = uri
                }

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.BottomCenter)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = "SJ",
                            color = Color(0xFF6A5ACD),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    // Edit Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile Picture",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Body Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Editable Information Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF6A5ACD))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Editable Information",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D3436)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Full Name
                        Text("Full Name", color = Color.Gray, fontSize = 12.sp)
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA),
                                focusedBorderColor = Color(0xFFE0E0E0),
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        // Age
                        Text("Age", color = Color.Gray, fontSize = 12.sp)
                        OutlinedTextField(
                            value = age,
                            onValueChange = { age = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                             colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA),
                                focusedBorderColor = Color(0xFFE0E0E0),
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Gender
                        Text("Gender", color = Color.Gray, fontSize = 12.sp)
                        ExposedDropdownMenuBox(
                            expanded = expandedGender,
                            onExpandedChange = { expandedGender = !expandedGender }
                        ) {
                            OutlinedTextField(
                                value = gender,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8F9FA),
                                    unfocusedContainerColor = Color(0xFFF8F9FA),
                                    focusedBorderColor = Color(0xFFE0E0E0),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGender,
                                onDismissRequest = { expandedGender = false }
                            ) {
                                genderOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            gender = option
                                            expandedGender = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Specialization
                        Text("Specialization", color = Color.Gray, fontSize = 12.sp)
                        ExposedDropdownMenuBox(
                            expanded = expandedSpec,
                            onExpandedChange = { expandedSpec = !expandedSpec }
                        ) {
                            OutlinedTextField(
                                value = specialization,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSpec) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF8F9FA),
                                    unfocusedContainerColor = Color(0xFFF8F9FA),
                                    focusedBorderColor = Color(0xFFE0E0E0),
                                    unfocusedBorderColor = Color(0xFFE0E0E0)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedSpec,
                                onDismissRequest = { expandedSpec = false }
                            ) {
                                specializationOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            specialization = option
                                            expandedSpec = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Protected Information Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F0FF)), // Light Lavender
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDCD0FF)) // Light Purple Border
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color(0xFF6A5ACD))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Protected Information",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D3436)
                            )
                        }
                        
                        Text(
                            text = "These fields are system-generated and cannot be modified",
                            color = Color(0xFF483D8B), // Dark Slate Blue
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                        )

                        // Email
                        Text("Email Address", color = Color.Gray, fontSize = 12.sp)
                        OutlinedTextField(
                            value = "sarah.johnson@gmail.com",
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color(0xFF6A5ACD)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = Color.White.copy(alpha = 0.5f), // Slightly transparent white
                                disabledBorderColor = Color.Transparent,
                                disabledTextColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Doctor ID
                        Text("Doctor ID", color = Color.Gray, fontSize = 12.sp)
                        OutlinedTextField(
                            value = "DOC-2024-8756",
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                             trailingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color(0xFF6A5ACD)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = Color.White.copy(alpha = 0.5f),
                                disabledBorderColor = Color.Transparent,
                                disabledTextColor = Color.Gray
                            )
                        )
                         Text(
                            text = "This field cannot be edited",
                            color = Color.Gray,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
