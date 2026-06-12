package com.Ojiem.myfirstapp.ui.theme.screens.loginscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.data.AuthViewModel
import com.Ojiem.myfirstapp.navigation.ROUTE_REGISTER
import com.Ojiem.myfirstapp.ui.theme.*

@Composable
fun LoginScreen(navHostController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authViewModel = AuthViewModel(navHostController, context)

    TheHubBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Authentication", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
            Text("Provide your node credentials", color = Color.Cyan.copy(alpha = 0.7f), fontSize = 14.sp)

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphism(RoundedCornerShape(32.dp))
                    .padding(24.dp)
            ) {
                Column {
                    HubTextField(value = email, onValueChange = { email = it }, label = "Email or Username", icon = Icons.Default.Person)
                    Spacer(modifier = Modifier.height(20.dp))
                    HubTextField(value = password, onValueChange = { password = it }, label = "Password", icon = Icons.Default.Lock, isPassword = true)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                authViewModel.login(email.trim(), password.trim())
            },
                enabled = !authViewModel.isLoading,
                modifier= Modifier.width(300.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red),
                shape = RoundedCornerShape(20.dp)
            ) {
                if (authViewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Login", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navHostController.navigate(ROUTE_REGISTER) },
                enabled = !authViewModel.isLoading
            ) {
                Text("New Agent? Register Here", color = Color.Cyan.copy(alpha = 0.8f), fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun HubTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White.copy(alpha = 0.5f)) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color.Cyan.copy(alpha = 0.7f)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Cyan,
            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.Cyan,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}
