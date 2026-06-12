package com.Ojiem.myfirstapp.ui.theme.screens.SafaricomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground

@Composable
fun SafaricomScreen(navController: NavController) {
    TheHubBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
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
            Text("Financial Node", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Text("Secure Transaction Interface", color = Color.Cyan, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // Balance Card
            Card(
                modifier = Modifier.fillMaxWidth().height(180.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
                    Text("CREDIT BALANCE", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("₵ 1,234,567.89", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null, tint = Color.Green, modifier = Modifier.size(16.dp))
                        Text(" +2.4% since last sync", color = Color.Green, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = Color.Cyan, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text("SCAN TO PAY", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SafaricomScreenPreview() {
    SafaricomScreen(rememberNavController())
}
