package com.Ojiem.myfirstapp.ui.theme.screens.registerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.data.AuthViewModel
import com.Ojiem.myfirstapp.navigation.ROUTE_LOGIN
import com.Ojiem.myfirstapp.ui.theme.ButtonGradientColors
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.glassmorphism
import com.Ojiem.myfirstapp.ui.theme.screens.loginscreen.HubTextField
import com.Ojiem.myfirstapp.ui.theme.shiftingGradient

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authViewModel = AuthViewModel(navHostController, context)

    TheHubBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registration", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Text("Join the quantum network", color = Color.Cyan.copy(alpha = 0.7f), fontSize = 14.sp)

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphism(RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    HubTextField(value = name, onValueChange = { name = it }, label = "Full Name", icon = Icons.Default.Person)
                    Spacer(modifier = Modifier.height(12.dp))
                    HubTextField(value = username, onValueChange = { username = it }, label = "Username", icon = Icons.Default.Tag)
                    Spacer(modifier = Modifier.height(12.dp))
                    HubTextField(value = email, onValueChange = { email = it }, label = "Secure Email", icon = Icons.Default.Email)
                    Spacer(modifier = Modifier.height(12.dp))
                    HubTextField(value = password, onValueChange = { password = it }, label = "Password", icon = Icons.Default.Lock, isPassword = true)
                    Spacer(modifier = Modifier.height(12.dp))
                    HubTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = "Confirm Password", icon = Icons.Default.Lock, isPassword = true)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            val brush = shiftingGradient(ButtonGradientColors)
            Button(
                onClick = { authViewModel.signup(name, username, email, password, confirmPassword) },
                enabled = !authViewModel.isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp).background(brush, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (authViewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Register", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            TextButton(
                onClick = { navHostController.navigate(ROUTE_LOGIN) },
                enabled = !authViewModel.isLoading
            ) {
                Text("Already an Agent? Authenticate", color = Color.Cyan.copy(alpha = 0.8f))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}
