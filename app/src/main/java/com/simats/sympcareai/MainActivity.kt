package com.simats.sympcareai


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.simats.sympcareai.ui.theme.SympcareAITheme
import androidx.compose.ui.graphics.Color

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Force Light Theme as per user request to remove Dark Mode feature
            SympcareAITheme(darkTheme = false) {
                // Manual BackStack Implementation
                val backStack = remember { mutableStateListOf(Screen.Splash) }
                var selectedSymptoms by remember { mutableStateOf(emptyList<String>()) }
                
                // Active Patients List (Hoisted State)
                val patients = remember { mutableStateListOf(
                    DoctorPatient("1", "John Doe", "Headache", "Now", "Online", Color(0xFFE0F2F1), Color(0xFF009688), "J", Color(0xFF00BFA5)),
                    DoctorPatient("2", "Priya Sharma", "Fever", "10m", "Completed", Color(0xFFE8EAF6), Color(0xFF3F51B5), "P", Color(0xFF448AFF)),
                    DoctorPatient("3", "Alex Chen", "Wellness", "15m", "Waiting", Color(0xFFFFF8E1), Color(0xFFFF8F00), "A", Color(0xFFFFB300))
                ) }
                
                var patientListFilter by remember { mutableStateOf("Total") } // "Total", "Pending", "Completed"
                
                // Derived counts
                val patientsTodayCount = patients.count { it.status != "Completed" }
                val completedPatientsCount = patients.count { it.status == "Completed" }

                val viewedPatientIds = remember { mutableStateListOf<String>() }
                val currentScreen = backStack.lastOrNull() ?: Screen.Splash

                // Global BackHandler
                // Enabled only whenever there's more than 1 screen in the stack
                BackHandler(enabled = backStack.size > 1) {
                    backStack.removeLast()
                }

                // Helper to navigate forward
                fun navigateTo(screen: Screen) {
                    backStack.add(screen)
                }

                // Helper to replace current screen (e.g. Splash -> Intro)
                fun replaceWith(screen: Screen) {
                    if (backStack.isNotEmpty()) {
                        backStack.removeLast()
                    }
                    backStack.add(screen)
                }
                
                // Helper to reset to a specific screen (clearing stack)
                fun resetTo(screen: Screen) {
                    backStack.clear()
                    backStack.add(screen)
                }

                fun navigateBack() {
                    if (backStack.size > 1) {
                        backStack.removeLast()
                    }
                }

                when (currentScreen) {
                    Screen.Splash -> {
                        // Splash -> Login (Directly, bypassing Intro)
                        SplashScreen(onTimeout = { replaceWith(Screen.Login) })
                    }

                    Screen.Login -> {
                        LoginScreen(
                            onDoctorSignUpClick = { navigateTo(Screen.DoctorSignUp) },
                            onDoctorForgotPasswordClick = { navigateTo(Screen.DoctorForgotPassword) },
                            onDoctorLoginSuccess = { resetTo(Screen.DoctorHome) },
                            onPatientSignUpClick = { navigateTo(Screen.PatientSignUp) },
                            onPatientForgotPasswordClick = { navigateTo(Screen.PatientForgotPassword) },
                            onPatientLoginSuccess = { resetTo(Screen.PatientHome) },
                            initialPage = 0 // Default to Patient
                        )
                    }
                    Screen.DoctorHome -> {
                        DoctorHomeScreen(
                            onPatientPortalClick = { navigateTo(Screen.DoctorPatientPortal) },
                            onChatClick = { navigateTo(Screen.DoctorChat) },
                            onNavigateTo = { screen -> 
                                if (screen == Screen.DoctorPatients) patientListFilter = "Total"
                                navigateTo(screen) 
                            },
                            onProfileClick = { navigateTo(Screen.DoctorProfile) },
                            onNotificationClick = { navigateTo(Screen.Notifications) },
                            onPatientsTodayClick = {
                                patientListFilter = "Pending"
                                navigateTo(Screen.DoctorPatients)
                            },
                            onCompletedPatientsClick = {
                                patientListFilter = "Completed"
                                navigateTo(Screen.DoctorPatients)
                            },
                            patientsTodayCount = patientsTodayCount,
                            completedPatientsCount = completedPatientsCount
                        )
                    }
                    Screen.DoctorProfile -> {
                        DoctorProfileScreen(
                            onBackClick = { navigateBack() },
                            onEditClick = { navigateTo(Screen.EditDoctorProfile) },
                            onChatClick = { navigateTo(Screen.DoctorChat) },
                            onNotificationClick = { navigateTo(Screen.Notifications) }
                        )
                    }
                    Screen.EditDoctorProfile -> {
                        EditDoctorProfileScreen(
                            onBackClick = { navigateBack() }
                        )
                    }
                    Screen.DoctorPatients -> {
                        DoctorPatientsScreen(
                            onNavigateTo = { screen ->
                                if (screen == Screen.DoctorPatients) patientListFilter = "Total"
                                navigateTo(screen)
                            },
                            onBackClick = { navigateBack() },
                            onPatientComplete = { patient ->
                                // Update status to Completed
                                val index = patients.indexOfFirst { it.id == patient.id }
                                if (index != -1) {
                                    patients[index] = patients[index].copy(
                                        status = "Completed",
                                        statusColor = Color(0xFFE8EAF6),
                                        statusTextColor = Color(0xFF3F51B5)
                                    )
                                }
                            },
                            patients = patients,
                            filter = patientListFilter
                        )
                    }
                    Screen.DoctorPatientPortal -> {
                        DoctorPatientPortalScreen(
                            onBackClick = { navigateBack() },
                            onViewPatientDetails = { patientId ->
                                if (patientId.isNotEmpty() && !viewedPatientIds.contains(patientId)) {
                                    viewedPatientIds.add(patientId)
                                    // Add new patient to list
                                    patients.add(0, DoctorPatient(
                                        id = patientId,
                                        name = "Patient $patientId", // Dummy name
                                        symptom = "Unknown",
                                        time = "Now",
                                        status = "Online",
                                        statusColor = Color(0xFFE0F2F1),
                                        statusTextColor = Color(0xFF009688),
                                        initial = "N",
                                        initialBg = Color(0xFF00BFA5)
                                    ))
                                }
                                navigateTo(Screen.DoctorPatientDetails)
                            },
                            onCreateNewPatient = { navigateTo(Screen.CreatePatient) }
                        )
                    }
                    Screen.CreatePatient -> {
                        CreatePatientScreen(
                            onBackClick = { navigateBack() },
                            onSubmitAndAnalyze = { id, symptoms -> navigateTo(Screen.AIPatientAnalysis) }
                        )
                    }
                    Screen.AIPatientAnalysis -> {
                        AIPatientAnalysisScreen(
                            onBackClick = { navigateBack() } // Or navigateTo(Screen.DoctorPatientDetails) if that's the flow? 
                            // Req: "submit and analyse it should navigated to AI analysis" 
                            // then user can probably go back or to details. 
                            // Let's just go back for now or I could add "View Details" logic here too.
                            // The design shows a Back button.
                        )
                    }
                    Screen.DoctorPatientDetails -> {
                        DoctorPatientDetailsScreen(
                            onBackClick = { navigateBack() }
                        )
                    }
// Removed PatientLogin route as it's merged into Login

                    Screen.DoctorSignUp -> {
                        DoctorSignUpScreen(
                            onSignInClick = { navigateBack() }, 
                            onSignUpSuccess = { navigateTo(Screen.DoctorDisclaimer) }
                        )
                    }
                    Screen.PatientSignUp -> {
                        PatientSignUpScreen(
                            onSignInClick = { navigateBack() },
                            onSignUpSuccess = { navigateTo(Screen.PatientDisclaimer) }
                        )
                    }
                    Screen.DoctorForgotPassword -> {
                        DoctorForgotPasswordScreen(
                            onCancelClick = { navigateBack() },
                            onSendOtpClick = { navigateTo(Screen.DoctorResetPassword) }
                        )
                    }
                    Screen.DoctorResetPassword -> {
                        DoctorResetPasswordScreen(
                            onBackClick = { navigateBack() },
                            onSaveClick = { resetTo(Screen.Login) }
                        )
                    }
                    Screen.PatientForgotPassword -> {
                        PatientForgotPasswordScreen(
                            onCancelClick = { navigateBack() },
                            onSendOtpClick = { navigateTo(Screen.PatientResetPassword) }
                        )
                    }
                    Screen.PatientResetPassword -> {
                        PatientResetPasswordScreen(
                            onBackClick = { navigateBack() },
                            onSaveClick = { resetTo(Screen.Login) }
                        )
                    }
                    Screen.DoctorDisclaimer -> {
                        DisclaimerScreen(
                            onAcceptClick = { resetTo(Screen.Login) }, // Accepted -> Login (Reset to avoid back loops)
                            isDoctor = true
                        )
                    }
                    Screen.PatientDisclaimer -> {
                        DisclaimerScreen(
                            onAcceptClick = { navigateTo(Screen.PatientHealthProfile) }, // Flow: Disclaimer -> Health Profile
                            isDoctor = false
                        )
                    }
                    Screen.PatientHealthProfile -> {
                        PatientHealthProfileScreen(
                            onBackClick = { navigateBack() },
                            onSaveClick = { resetTo(Screen.PatientHome) } // Success -> Home (Reset)
                        )
                    }
                    Screen.PatientHome -> {
                        PatientHomeScreen(
                            onChatClick = { navigateTo(Screen.SymptomSelection) },
                            onVoiceClick = { navigateTo(Screen.VoiceAssistant) },
                            onHealthReportClick = { navigateTo(Screen.HealthReports) },
                            onProfileClick = { navigateTo(Screen.PatientProfile) },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onNotificationClick = { navigateTo(Screen.Notifications) }
                        )
                    }
                    Screen.Chat -> {
                        ChatScreen(
                            onBackClick = { navigateBack() },
                            onAttachmentClick = { navigateTo(Screen.UploadHealthFile) },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onFinishClick = { navigateTo(Screen.ReportAnalysis) },
                            selectedSymptoms = selectedSymptoms
                        )
                    }
                    Screen.VoiceAssistant -> {
                        VoiceAssistantScreen(
                            onBackClick = { navigateBack() },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            selectedSymptoms = selectedSymptoms
                        )
                    }
                    Screen.DoctorSettings -> {
                        DoctorSettingsScreen(
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onBackClick = { navigateBack() },
                            onLogoutClick = { resetTo(Screen.Login) }
                        )
                    }
                    Screen.PatientSettings -> {
                        PatientSettingsScreen(
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onBackClick = { navigateBack() },
                            onLogoutClick = { resetTo(Screen.Login) }
                        )
                    }
                    Screen.UploadHealthFile -> {
                         UploadHealthFileScreen(
                             onBackClick = { navigateBack() },
                             onUploadClick = { navigateTo(Screen.ReportAnalysis) }, // Success -> Report Analysis
                             onNavigateTo = { screen -> navigateTo(screen) }
                         )
                    }
                    Screen.ReportAnalysis -> {
                        ReportAnalysisScreen(
                            onBackClick = { navigateBack() },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onNextClick = { navigateTo(Screen.Feedback) },
                            selectedSymptoms = selectedSymptoms
                        )
                    }
                    Screen.Feedback -> {
                        FeedbackScreen(
                            onBackClick = { navigateBack() },
                            onSubmitClick = { navigateTo(Screen.PatientHome) }, // Submit -> Home
                            onNavigateTo = { screen -> navigateTo(screen) }
                        )
                    }
                    Screen.HealthReports -> {
                        HealthReportsScreen(
                            onBackClick = { navigateBack() },
                            onReportClick = { navigateTo(Screen.ReportAnalysis) },
                            onNavigateTo = { screen -> navigateTo(screen) }
                        )
                    }
                    Screen.PatientProfile -> {
                        PatientProfileScreen(
                            onBackClick = { navigateBack() },
                            onEditClick = { navigateTo(Screen.EditPatientProfile) },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onNotificationClick = { navigateTo(Screen.Notifications) }
                        )
                    }
                    Screen.EditPatientProfile -> {
                        EditPatientProfileScreen(
                            onBackClick = { navigateBack() },
                            onSaveClick = { navigateBack() } // Success -> Return to Profile
                            
                        )
                    }


                    Screen.ChatHistory -> {
                        ChatHistoryScreen(
                            onBackClick = { navigateBack() },
                            onChatClick = { navigateTo(Screen.ChatReadOnly) },
                            onNavigateTo = { screen -> navigateTo(screen) }
                        )
                    }
                    Screen.ChatReadOnly -> {
                        ChatScreen(
                            onBackClick = { navigateBack() },
                            onAttachmentClick = {},
                            onNavigateTo = { screen -> navigateTo(screen) },
                            onFinishClick = {}, // Hide finish button if needed or handle logic
                            isReadOnly = true
                        )
                    }
                    Screen.HealthMonitor -> {
                        HealthMonitorScreen(
                            onBackClick = { resetTo(Screen.PatientHome) },
                            onNavigateTo = { screen -> navigateTo(screen) }
                        )
                    }
                    Screen.HealthAssessment -> {
                        HealthAssessmentScreen(
                            onBackClick = { navigateBack() },
                            onNextClick = { navigateTo(Screen.HealthAnalysisResult) }
                        )
                    }
                    Screen.HealthAnalysisResult -> {
                        HealthAnalysisResultScreen(
                            onCloseClick = { resetTo(Screen.HealthMonitor) },
                            onStartNewSessionClick = { 
                                // Reset to assessment or monitor
                                navigateTo(Screen.HealthAssessment) 
                            }
                        )
                    }
                    Screen.DoctorChat -> {
                        DoctorChatScreen(
                            onBackClick = { navigateBack() },
                            onNavigateTo = { screen -> navigateTo(screen) }
                        )
                    }
                    Screen.SymptomSelection -> {
                        SymptomSelectionScreen(
                            onBackClick = { navigateBack() },
                            onContinueClick = { symptoms -> 
                                selectedSymptoms = symptoms
                                navigateTo(Screen.Chat) 
                            }
                        )
                    }
                    Screen.VoiceSymptomSelection -> {
                        SymptomSelectionScreen(
                            onBackClick = { navigateBack() },
                            onContinueClick = { symptoms ->
                                selectedSymptoms = symptoms
                                navigateTo(Screen.VoiceAssistant)
                            }
                        )
                    }
                    Screen.AccountSettings -> {
                        // Default fallback, though should use specific routes
                        AccountSettingsScreen(
                            onBackClick = { navigateBack() },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            isDoctor = false // Default
                        )
                    }
                    Screen.PatientAccountSettings -> {
                        AccountSettingsScreen(
                            onBackClick = { navigateBack() },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            isDoctor = false
                        )
                    }
                    Screen.DoctorAccountSettings -> {
                        AccountSettingsScreen(
                            onBackClick = { navigateBack() },
                            onNavigateTo = { screen -> navigateTo(screen) },
                            isDoctor = true
                        )
                    }
                    Screen.ResetPassword -> {
                         // Fallback or handle based on context if possible, but better to use specific routes
                        ResetPasswordScreen(
                            onBackClick = { navigateBack() },
                            onSuccess = { navigateBack() },
                            isDoctor = false 
                        )
                    }
                    Screen.PatientResetPasswordLoggedIn -> {
                        ResetPasswordScreen(
                            onBackClick = { navigateBack() },
                            onSuccess = { navigateBack() },
                            isDoctor = false
                        )
                    }
                    Screen.DoctorResetPasswordLoggedIn -> {
                        ResetPasswordScreen(
                            onBackClick = { navigateBack() },
                            onSuccess = { navigateBack() },
                            isDoctor = true
                        )
                    }
                    Screen.AboutApp -> {
                        AboutAppScreen(onBackClick = { navigateBack() })
                    }
                    Screen.TermsAndConditions -> {
                        TermsAndConditionsScreen(onBackClick = { navigateBack() })
                    }
                    Screen.Notifications -> {
                        NotificationScreen(onBackClick = { navigateBack() })
                    }
                    Screen.NotificationSettings -> {
                        NotificationSettingsScreen(onBackClick = { navigateBack() })
                    }
                    Screen.DataAndPrivacy -> {
                        DataPrivacyScreen(onBackClick = { navigateBack() })
                    }
                }
            }
        }
    }
}

enum class Screen {
    Splash, Login, DoctorSignUp, PatientSignUp, DoctorForgotPassword, PatientForgotPassword, PatientResetPassword, DoctorResetPassword, DoctorDisclaimer, PatientDisclaimer, PatientHealthProfile, PatientHome, DoctorHome, DoctorProfile, EditDoctorProfile, DoctorPatients, DoctorPatientPortal, CreatePatient, AIPatientAnalysis, DoctorPatientDetails, Chat, VoiceAssistant, DoctorSettings, PatientSettings, DoctorChat, UploadHealthFile, ReportAnalysis, Feedback, HealthReports, PatientProfile, EditPatientProfile, ChatHistory, ChatReadOnly, HealthMonitor, HealthAssessment, HealthAnalysisResult, SymptomSelection, VoiceSymptomSelection, AccountSettings, ResetPassword, PatientAccountSettings, DoctorAccountSettings, PatientResetPasswordLoggedIn, DoctorResetPasswordLoggedIn, AboutApp, TermsAndConditions, Notifications, NotificationSettings, DataAndPrivacy
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SympcareAITheme {
        Greeting("Android")
    }
}