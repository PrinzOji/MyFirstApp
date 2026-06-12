package com.Ojiem.myfirstapp.ui.theme.screens.products

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.Ojiem.myfirstapp.data.ProductViewModel
import com.Ojiem.myfirstapp.ui.theme.ButtonGradientColors
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.glassmorphism
import com.Ojiem.myfirstapp.ui.theme.screens.loginscreen.HubTextField
import com.Ojiem.myfirstapp.ui.theme.shiftingGradient

@Composable
fun AddProductScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }
    
    val context = LocalContext.current
    val productViewModel = remember { ProductViewModel(navController, context) }
    val isPreview = LocalInspectionMode.current

    LaunchedEffect(Unit) {
        if (!isPreview) {
            productViewModel.checkLogin()
        }
    }

    TheHubBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
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
                Text("Product Inventory", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Image Picker
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .glassmorphism(CircleShape)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri.value != null) {
                    AsyncImage(
                        model = imageUri.value,
                        contentDescription = "Product Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = Color.Cyan, modifier = Modifier.size(40.dp))
                        Text("Add Image", color = Color.Cyan.copy(alpha = 0.7f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassmorphism(RoundedCornerShape(32.dp))
                    .padding(24.dp)
            ) {
                Column {
                    HubTextField(value = name, onValueChange = { name = it }, label = "Product Name", icon = Icons.Default.Inventory)
                    Spacer(modifier = Modifier.height(16.dp))
                    HubTextField(value = category, onValueChange = { category = it }, label = "Category", icon = Icons.Default.Category)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            HubTextField(
                                value = quantity, 
                                onValueChange = { quantity = it }, 
                                label = "Qty", 
                                icon = Icons.Default.Numbers,
                                isPassword = false
                            )
                        }
                        Box(modifier = Modifier.weight(1.5f)) {
                            HubTextField(
                                value = price, 
                                onValueChange = { price = it }, 
                                label = "Price", 
                                icon = Icons.Default.AttachMoney
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Product Description", color = Color.White.copy(alpha = 0.5f)) },
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Cyan,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.Cyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            val brush = shiftingGradient(ButtonGradientColors)
            Button(
                onClick = {
                    productViewModel.addProduct(name, category, price, quantity, description, imageUri.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(brush, RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Sync to Repository", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductScreenPreview() {
    AddProductScreen(rememberNavController())
}
