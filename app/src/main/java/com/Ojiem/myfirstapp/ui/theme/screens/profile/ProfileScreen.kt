package com.Ojiem.myfirstapp.ui.theme.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.glassmorphism
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val email = user?.email ?: "Unknown Agent"
    var showDetails by remember { mutableStateOf(false) }

    TheHubBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.glassmorphism(CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Agent Dossier", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Dossier Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphism(RoundedCornerShape(32.dp))
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .glassmorphism(CircleShape)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = Color.Cyan,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("CLASSIFIED STATUS", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("ACTIVE AGENT", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

                    Spacer(modifier = Modifier.height(32.dp))

                    DossierItem("Network ID", if (showDetails) email else "**********")
                    DossierItem("Access Level", "LVL 4 - ENCRYPTED")
                    DossierItem("Node Origin", "KEN-HQ-01")

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { showDetails = !showDetails },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showDetails) Color.Red.copy(alpha = 0.2f) else Color.Cyan.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, if (showDetails) Color.Red else Color.Cyan)
                    ) {
                        Icon(
                            if (showDetails) Icons.Default.Security else Icons.Default.Fingerprint,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(if (showDetails) "Conceal Identity" else "Authorize Clearance")
                    }
                }
            }
        }
    }
}

@Composable
fun DossierItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}
