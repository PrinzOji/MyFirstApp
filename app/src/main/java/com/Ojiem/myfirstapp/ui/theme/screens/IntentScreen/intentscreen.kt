package com.Ojiem.myfirstapp.ui.theme.screens.intentscreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground

@Composable
fun IntentScreen(navController: NavHostController) {
    val context = LocalContext.current

    TheHubBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            Text("System Protocols", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(32.dp))

            val items = listOf(
                Protocol("Sim Toolkit", Icons.Default.SimCard, Color(0xFFF09819)) {
                    val intent = context.packageManager.getLaunchIntentForPackage("com.android.stk")
                    intent?.let { context.startActivity(it) }
                },
                Protocol("Direct Call", Icons.Default.Call, Color(0xFF00E5FF)) {
                    context.startActivity(Intent(Intent.ACTION_DIAL).apply { data = "tel:0757036970".toUri() })
                },
                Protocol("Secure Mail", Icons.Default.Email, Color(0xFFD500F9)) {
                    context.startActivity(Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("chulabaraka12148@gmail.com"))
                    })
                },
                Protocol("Data Broadcast", Icons.Default.Share, Color(0xFF76FF03)) {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Sync complete.")
                    }
                    context.startActivity(Intent.createChooser(intent, "Share"))
                }
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items.forEach { ProtocolItem(it) }
            }
        }
    }
}

data class Protocol(val label: String, val icon: ImageVector, val color: Color, val action: () -> Unit)

@Composable
fun ProtocolItem(protocol: Protocol) {
    Card(
        modifier = Modifier.fillMaxWidth().height(72.dp).clickable { protocol.action() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(protocol.color.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(protocol.icon, contentDescription = null, tint = protocol.color)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(protocol.label, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IntentScreenPreview() {
    IntentScreen(rememberNavController())
}
