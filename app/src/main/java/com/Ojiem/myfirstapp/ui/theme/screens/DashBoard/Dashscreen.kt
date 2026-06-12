package com.Ojiem.myfirstapp.ui.theme.screens.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.data.AuthViewModel
import com.Ojiem.myfirstapp.navigation.ROUTE_CALCULATOR
import com.Ojiem.myfirstapp.navigation.ROUTE_HOME
import com.Ojiem.myfirstapp.navigation.ROUTE_INTENT
import com.Ojiem.myfirstapp.navigation.ROUTE_REGISTER
import com.Ojiem.myfirstapp.navigation.ROUTE_SAFARICOM
import com.Ojiem.myfirstapp.navigation.ROUTE_ADD_PRODUCT
import com.Ojiem.myfirstapp.navigation.ROUTE_VIEW_PRODUCT
import com.Ojiem.myfirstapp.navigation.ROUTE_PROFILE
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.glassmorphism
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    var likes by remember { mutableIntStateOf(0) }
    var isSyncing by remember { mutableStateOf(false) }
    var showCriticalData by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val authViewModel = AuthViewModel(navController as NavHostController, context)
    val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: "Unknown Agent"

    val maskedEmail = remember(userEmail) {
        if (userEmail.contains("@")) {
            val parts = userEmail.split("@")
            val name = parts[0]
            if (name.length > 2) {
                "${name[0]}***${name.last()}@${parts[1]}"
            } else {
                "***@${parts[1]}"
            }
        } else userEmail
    }

    LaunchedEffect(Unit) { visible = true }

    TheHubBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp).verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = visible, enter = fadeIn() + slideInVertically()) {
                    Column(
                        modifier = Modifier.clickable { navController.navigate(ROUTE_PROFILE) }
                    ) {
                        Text("Control Panel", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Agent: ${if (showCriticalData) userEmail else maskedEmail}",
                                color = Color.Cyan.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = if (showCriticalData) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle Privacy",
                                tint = Color.Cyan.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { showCriticalData = !showCriticalData }
                            )
                        }
                    }
                }
                
                Row {
                    IconButton(
                        onClick = {
                            scope.launch {
                                isSyncing = true
                                delay(2000)
                                isSyncing = false
                            }
                        },
                        modifier = Modifier.glassmorphism(CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Sync,
                            contentDescription = "Sync",
                            tint = if (isSyncing) Color.Yellow else Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(
                        onClick = { authViewModel.logout() },
                        modifier = Modifier.glassmorphism(CircleShape, containerColor = Color.Red.copy(alpha = 0.1f))
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Red.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Status Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphism(RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = if (isSyncing) "CORE STATUS: SYNCHRONIZING..." else "CORE STATUS: SECURE",
                        color = if (isSyncing) Color.Yellow else Color.Green,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Community Pulse", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                            Text("$likes Global Likes", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { likes++ },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta.copy(alpha = 0.2f)),
                            modifier = Modifier.border(1.dp, Color.Magenta.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Like")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Network Intel Section
            Text("Network Intel", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("Agent Alpha", "Agent Zero", "Agent X").forEach { agent ->
                    AgentStatusCard(agent)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            val nodes = listOf(
                DashNode("Interface", Icons.Default.GridView, Color(0xFF00C6FF)) { navController.navigate(ROUTE_HOME) },
                DashNode("Repository", Icons.Default.Inventory, Color(0xFF00E676)) { navController.navigate(ROUTE_VIEW_PRODUCT) },
                DashNode("Add Record", Icons.Default.AddBusiness, Color(0xFFD500F9)) { navController.navigate(ROUTE_ADD_PRODUCT) },
                DashNode("Intents", Icons.Default.SwapHoriz, Color(0xFFFFFF00)) { navController.navigate(ROUTE_INTENT) },
                DashNode("Calculator", Icons.Default.Calculate, Color(0xFF76FF03)) { navController.navigate(ROUTE_CALCULATOR) },
                DashNode("Safaricom", Icons.Default.AccountBalanceWallet, Color(0xFFFF4081)) { navController.navigate(ROUTE_SAFARICOM) }
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                for (i in nodes.indices step 2) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        NodeCard(nodes[i], Modifier.weight(1f))
                        if (i + 1 < nodes.size) {
                            NodeCard(nodes[i + 1], Modifier.weight(1f))
                        } else {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

data class DashNode(val title: String, val icon: ImageVector, val color: Color, val action: () -> Unit)

@Composable
fun NodeCard(node: DashNode, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(160.dp)
            .glassmorphism(RoundedCornerShape(28.dp))
            .clip(RoundedCornerShape(28.dp))
            .clickable { node.action() }
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(node.color.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
                    .border(1.dp, node.color.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(node.icon, contentDescription = null, tint = node.color)
            }
            Text(node.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AgentStatusCard(name: String) {
    Box(
        modifier = Modifier
            .width(110.dp)
            .glassmorphism(RoundedCornerShape(16.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Green, CircleShape)
                    .align(Alignment.End)
            )
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.Cyan.copy(alpha = 0.6f),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(name, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashScreenPreview() {
    DashScreen(rememberNavController())
}
