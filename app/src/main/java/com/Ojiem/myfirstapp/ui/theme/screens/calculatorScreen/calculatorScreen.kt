package com.Ojiem.myfirstapp.ui.theme.screens.calculatorScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.ui.theme.ButtonGradientColors
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.shiftingGradient

@Composable
fun CalculatorScreen(navController: NavHostController) {
    var n1 by remember { mutableStateOf("") }
    var n2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Ready") }

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
            Text("Logic Processor", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(result, color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = n1,
                onValueChange = { n1 = it },
                label = { Text("Value A", color = Color.Cyan.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = n2,
                onValueChange = { n2 = it },
                label = { Text("Value B", color = Color.Cyan.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            val brush = shiftingGradient(ButtonGradientColors)
            val ops = listOf("+", "-", "*", "/")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ops.forEach { op ->
                    Button(
                        onClick = {
                            val v1 = n1.toDoubleOrNull()
                            val v2 = n2.toDoubleOrNull()
                            if (v1 != null && v2 != null) {
                                result = when(op) {
                                    "+" -> (v1 + v2).toString()
                                    "-" -> (v1 - v2).toString()
                                    "*" -> (v1 * v2).toString()
                                    "/" -> if(v2 != 0.0) (v1 / v2).toString() else "Error"
                                    else -> "Error"
                                }
                            } else result = "Input Error"
                        },
                        modifier = Modifier.weight(1f).height(56.dp).background(brush, RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(op, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen(rememberNavController())
}
