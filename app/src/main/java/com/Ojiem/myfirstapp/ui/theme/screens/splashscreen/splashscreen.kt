package com.Ojiem.myfirstapp.ui.theme.screens.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Ojiem.myfirstapp.navigation.ROUTE_DASHBOARD
import com.Ojiem.myfirstapp.navigation.ROUTE_LOGIN
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) }
    var startAnimation by remember { mutableStateOf(false) }
    
    val progressAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 10000, easing = LinearEasing),
        label = "progress"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(1200, easing = { OvershootInterpolator(4f).getInterpolation(it) })
        )
        delay(10000L) // 10 second delay
        
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navController.navigate(ROUTE_DASHBOARD) {
                popUpTo(0) { inclusive = true }
            }
        } else {
            navController.navigate(ROUTE_LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    TheHubBackground {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TheHub",
                    color = Color.White,
                    fontSize = 84.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.scale(scale.value)
                )
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Text(
                    text = "Initializing Quantum Core...",
                    color = Color.Cyan.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LinearProgressIndicator(
                    progress = { progressAnimation },
                    modifier = Modifier
                        .width(240.dp)
                        .height(4.dp),
                    color = Color.Cyan,
                    trackColor = Color.White.copy(alpha = 0.1f),
                    strokeCap = StrokeCap.Round
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "${(progressAnimation * 100).toInt()}%",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
