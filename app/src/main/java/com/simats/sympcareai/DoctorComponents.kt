package com.simats.sympcareai

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle

@Composable
fun ActivePatientCard(
    name: String,
    symptom: String,
    time: String,
    status: String,
    statusColor: Color,
    statusTextColor: Color,
    initial: String,
    initialBg: Color,
    onMarkComplete: (() -> Unit)? = null // Optional callback
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }, // Toggle dropdown on click
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = initialBg,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = initial, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = symptom, color = Color.Gray, fontSize = 12.sp)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(text = time, color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = statusColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = status,
                        color = statusTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }

    // Context Menu
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        containerColor = Color.White
    ) {
        DropdownMenuItem(
            text = { Text("Mark as Complete") },
            onClick = {
                onMarkComplete?.invoke()
                expanded = false
            },
            leadingIcon = {
                 Icon(
                    imageVector = Icons.Outlined.CheckCircle, 
                    contentDescription = null,
                    tint = Color(0xFF00BFA5)
                ) 
            }
        )
    }
  }
}

@Composable
fun QuickActionCard(
    icon: ImageVector,
    label: String,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp), // Rounded square look
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
             Surface(
                shape = RoundedCornerShape(12.dp),
                color = iconColor.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                 Box(contentAlignment = Alignment.Center) {
                     Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
                 }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}
