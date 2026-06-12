package com.Ojiem.myfirstapp.ui.theme.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.Ojiem.myfirstapp.R
import com.Ojiem.myfirstapp.data.ProductViewModel
import com.Ojiem.myfirstapp.models.Product
import com.Ojiem.myfirstapp.navigation.ROUTE_ADD_PRODUCT
import com.Ojiem.myfirstapp.navigation.ROUTE_UPDATE_PRODUCT
import com.Ojiem.myfirstapp.ui.theme.TheHubBackground
import com.Ojiem.myfirstapp.ui.theme.glassmorphism

@Composable
fun ViewProductsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val productViewModel = remember { ProductViewModel(navController, context) }
    val products = remember { mutableStateListOf<Product>() }
    val isPreview = LocalInspectionMode.current
    
    LaunchedEffect(Unit) {
        if (!isPreview) {
            productViewModel.checkLogin()
            productViewModel.viewProducts(products)
        }
    }

    if (isPreview && products.isEmpty()) {
        products.add(Product("1", "Smart Watch", "Electronics", "199.99", "10", "A sample product"))
    }

    TheHubBackground {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(ROUTE_ADD_PRODUCT) },
                    containerColor = Color.Cyan.copy(alpha = 0.2f),
                    contentColor = Color.Cyan,
                    shape = CircleShape,
                    modifier = Modifier.glassmorphism(CircleShape)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
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
                    Text(
                        "Repository",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (products.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.Cyan)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        items(products) { product ->
                            ProductItem(
                                product = product,
                                onEdit = { navController.navigate("$ROUTE_UPDATE_PRODUCT/${product.id}") },
                                onDelete = { productViewModel.deleteProduct(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .glassmorphism(RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (product.imageUrl.isNotEmpty() && product.imageUrl != "null") {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.hub_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    product.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    product.category,
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        "$${product.price}",
                        color = Color.Cyan.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Qty: ${product.quantity}",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                }
            }

            IconButton(onClick = onEdit) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewProductsScreenPreview() {
    ViewProductsScreen(rememberNavController())
}
