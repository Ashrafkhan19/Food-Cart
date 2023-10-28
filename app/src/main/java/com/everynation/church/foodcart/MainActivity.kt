package com.everynation.church.foodcart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.everynation.church.foodcart.presentation.screens.CartScreen
import com.everynation.church.foodcart.presentation.screens.FavouriteScreen
import com.everynation.church.foodcart.presentation.screens.HomeScreen
import com.everynation.church.foodcart.ui.theme.FoodCartTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val nav = rememberNavController()
            FoodCartTheme {

                NavHost(navController = nav, startDestination = "home") {
                    composable("home") {
                        HomeScreen(onFavNavClick = {
                            nav.navigate("fav")
                        }, onCartNavClick = {
                            nav.navigate("cart")
                        })
                    }

                    composable("fav") {
                        FavouriteScreen(onBack = {nav.navigateUp()})
                    }

                    composable("cart") {
                        CartScreen(onBack = {nav.navigateUp()})
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodCartTheme {
        //HomePage(it = PaddingValues(), data = emptyMap(), onItemClick = { _, _->}) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewFoodItem() {
    FoodCartTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "My Store") },
                    modifier = Modifier.background(
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF5722), Color(0xFFE6DA75)),
                        )
                    ),
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu, contentDescription = "",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "",

                            )

                        Spacer(modifier = Modifier.width(8.dp))
                        BadgedBox(badge = {
                            Badge(modifier = Modifier.offset((-10).dp, 4.dp)) {
                                Text(text = "2")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = ""
                            )
                        }

                        Spacer(modifier = Modifier.width(18.dp))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }) {
            Column(modifier = Modifier.padding(it.calculateTopPadding())) {

            }
        }

    }
}