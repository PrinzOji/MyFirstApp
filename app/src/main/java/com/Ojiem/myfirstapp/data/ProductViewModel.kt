package com.Ojiem.myfirstapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.Ojiem.myfirstapp.models.Product
import com.Ojiem.myfirstapp.navigation.ROUTE_LOGIN
import com.Ojiem.myfirstapp.navigation.ROUTE_VIEW_PRODUCT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductViewModel(var navController: NavHostController, var context: Context) {
    var authViewModel: AuthViewModel = AuthViewModel(navController, context)

    fun checkLogin() {
        if (!authViewModel.isLogged()) {
            navController.navigate(ROUTE_LOGIN)
        }
    }

    fun addProduct(name: String, category: String, price: String, quantity: String, description: String, imageUri: Uri?) {
        val id = System.currentTimeMillis().toString()
        val productData = Product(id, name, category, price, quantity, description, imageUri.toString())
        val dbRef = FirebaseDatabase.getInstance().getReference("Products/$id")
        
        dbRef.setValue(productData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Product Uploaded", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_PRODUCT)
            } else {
                Toast.makeText(context, "Upload Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun viewProducts(products: SnapshotStateList<Product>): SnapshotStateList<Product> {
        val dbRef = FirebaseDatabase.getInstance().getReference("Products")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val product = snap.getValue(Product::class.java)
                    product?.let { products.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }

    fun deleteProduct(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Products/$id")
        dbRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Deletion Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateProduct(id: String, name: String, category: String, price: String, quantity: String, description: String, imageUrl: String) {
        val updateRef = FirebaseDatabase.getInstance().getReference("Products/$id")
        val updateData = Product(id, name, category, price, quantity, description, imageUrl)
        
        updateRef.setValue(updateData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Product Updated", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_VIEW_PRODUCT)
            } else {
                Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
