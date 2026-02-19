package com.simats.sympcareai

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(
    onBackClick: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    isDoctor: Boolean
) {
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    
    // Mock Data (Replace with real user data)
    val idLabel = if (isDoctor) "Doctor ID" else "Patient ID"
    val idValue = if (isDoctor) "DOC12345" else "PAT67890" // Mock ID
    val username = "johndoe_2024" // Keep username? Or replace entirely? Request says "instead of username change that as Patient id...". So replace.
    val name = "John Doe" // Keeping name for display header? The original code had username as header. Let's use ID as header or Name?
    // Let's use ID as the main identifier field.
    
    val userEmail = "johndoe@example.com"
    val userPassword = "MySecurePass123!" 
    
    val primaryColor = if (isDoctor) Color(0xFFE65100) else Color(0xFF009688)

    // Biometric Logic
    fun authenticate(onSuccess: () -> Unit) {
        val activity = context as? FragmentActivity ?: return
        val executor = ContextCompat.getMainExecutor(context)
        
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                    Toast.makeText(context, "Authentication Succeeded!", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Authentication Error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication Required")
            .setSubtitle("Confirm your identity to proceed")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Account Information", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Main Account Info Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = if (isDoctor) "Dr. John Doe" else "John Doe", // Mock Name
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

                    // ID Field
                    AccountField(
                        label = idLabel,
                        value = idValue,
                        icon = Icons.Outlined.Person,
                        helperText = "Your unique $idLabel cannot be changed",
                        iconTint = primaryColor
                    )

                    // Email Field
                    AccountField(
                        label = "Email Address",
                        value = userEmail,
                        icon = Icons.Outlined.Email,
                        helperText = "Primary email for notifications and recovery",
                        iconTint = primaryColor
                    )

                    // Password Field
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                           Icon(Icons.Outlined.Lock, contentDescription = null, tint = primaryColor, modifier = Modifier.size(18.dp))
                           Spacer(modifier = Modifier.width(8.dp))
                           Text("Password", fontSize = 14.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        OutlinedTextField(
                            value = if (isPasswordVisible) userPassword else "••••••••••••",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                IconButton(onClick = { 
                                    if (isPasswordVisible) {
                                        isPasswordVisible = false 
                                    } else {
                                        authenticate { isPasswordVisible = true }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle Password Visibility",
                                        tint = Color.Gray
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                focusedContainerColor = Color(0xFFFAFAFA)
                            )
                        )
                        
                        TextButton(onClick = { 
                            if (isDoctor) {
                                onNavigateTo(Screen.DoctorResetPasswordLoggedIn)
                            } else {
                                onNavigateTo(Screen.PatientResetPasswordLoggedIn)
                            }
                        }) {
                             Text("Reset password", color = primaryColor, fontSize = 14.sp)
                        }
                    }
                }
            }

            // Danger Zone Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE).copy(alpha = 0.3f)), // Light Red Tint
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFCDD2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFD32F2F))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Danger Zone", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Once you delete your account, there is no going back. All your health data, reports, and chat history will be permanently deleted.",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedButton(
                        onClick = { 
                            authenticate {
                                // TODO: Perform actual Account Deletion API call here
                                Toast.makeText(context, "Account Deleted (Mock)", Toast.LENGTH_LONG).show()
                                onNavigateTo(Screen.Login) // Navigate back to Login after mock deletion
                            } 
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFD32F2F),
                            containerColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD32F2F)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Delete Account", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountField(
    label: String,
    value: String,
    icon: ImageVector,
    helperText: String,
    iconTint: Color = Color(0xFF009688)
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
           Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
           Spacer(modifier = Modifier.width(8.dp))
           Text(label, fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                unfocusedContainerColor = Color(0xFFFAFAFA),
                focusedContainerColor = Color(0xFFFAFAFA)
            )
        )
        
        Text(
            text = helperText,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
        )
    }
}
