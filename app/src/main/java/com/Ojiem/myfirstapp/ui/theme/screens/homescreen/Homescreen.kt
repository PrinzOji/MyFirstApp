package com.Ojiem.myfirstapp.ui.theme.screens.homescreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.navigation.ROUTE_REGISTER
import com.Ojiem.myfirstapp.ui.theme.ButtonGradientColors
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.shiftingGradient

@Composable
fun HomeScreen(navController: NavHostController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    TheHubBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000)) + slideInVertically { -40 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Welcome to", color = Color.White.copy(alpha = 0.8f), fontSize = 20.sp)
                    Text(
                        "The Hub",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000, 500)) + slideInVertically { 40 }
            ) {
                val brush = shiftingGradient(ButtonGradientColors)
                Button(
                    onClick = { navController.navigate(ROUTE_REGISTER) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(brush, RoundedCornerShape(28.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Initialize Session", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
