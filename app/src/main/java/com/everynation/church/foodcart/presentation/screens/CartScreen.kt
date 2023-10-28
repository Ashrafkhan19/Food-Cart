package com.everynation.church.foodcart.presentation.screens

import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.everynation.church.foodcart.data.local.FoodItemEntity
import com.everynation.church.foodcart.presentation.MainViewModel
import com.everynation.church.foodcart.ui.theme.FoodCartTheme
import com.everynation.church.foodcart.ui.theme.Orange
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(onBack: () -> Unit) {
    val viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
    val cartData by viewModel.cartData.collectAsStateWithLifecycle()


    Scaffold(containerColor = MaterialTheme.colorScheme.background, topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            },
            modifier = Modifier,
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            onBack()
                        }
                )
            },
        )
    }) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    //.padding(top = it.calculateTopPadding())
                    .animateContentSize(),
                contentPadding = PaddingValues(bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartData, key = { it.id }) { food: FoodItemEntity ->
                    CartItem(foodItem = food, onFavClick = viewModel::onFavClick)
                }
            }

            CartTotal(modifier = Modifier, totalPrice = cartData.sumOf { it.price * 3 })
        }
    }
}

@Composable
fun CartTotal(modifier: Modifier = Modifier, totalPrice: Double) {
    Column(modifier = modifier) {
        Card(
            shape = MaterialTheme.shapes.large,

            ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp)
            ) {
                Row {
                    Text(text = "Sub Total", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = totalPrice.roundToInt().toString(), fontWeight = FontWeight.Medium)

                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = "Discount", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "40", fontWeight = FontWeight.Medium)

                }

                Spacer(modifier = Modifier.height(16.dp))

                Divider()

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${totalPrice.minus(40).roundToInt().takeIf { it > 0 } ?: 0}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                }

            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Checkout")
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
fun PreviewCartTotal() {
    FoodCartTheme {
        Surface {
            CartTotal(Modifier, 0.0)
        }
    }
}

@Composable
fun CartItem(foodItem: FoodItemEntity, onFavClick: (FoodItemEntity) -> Unit) {
    val color by animateColorAsState(
        targetValue = if (foodItem.isFav) Color.Red else Color.White, label = ""
    )
    Card {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = foodItem.icon,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(12.dp)
                    .weight(.3f)
            )
            Column(modifier = Modifier.weight(.6f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = foodItem.name,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )

                    //Spacer(modifier = Modifier.weight(1f))

                    Row(verticalAlignment = Alignment.CenterVertically, ) {

                        FilledIconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Orange, Color.White
                            ),
                            modifier = Modifier.size(32.dp),
                            onClick = { onFavClick(foodItem) },
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                        Text(
                            text = "03",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        FilledIconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Orange, Color.White
                            ),
                            modifier = Modifier.size(32.dp),
                            onClick = { onFavClick(foodItem) },
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                                modifier = Modifier.padding(2.dp),
                            )
                        }
                    }


                    Spacer(modifier = Modifier.weight(.1f))


                    /*Spacer(modifier = Modifier.height(12.dp))
                    Row {
    //                    Text(text = "1 Unit")
    //                    Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "₹${foodItem.price}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "/kg")
                    }
                    Spacer(modifier = Modifier.height(4.dp))*/
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row {
                        Text(
                            text = "₹${foodItem.price.roundToInt()}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        if(foodItem.categoryName == "Beverages") {
                            Text(text = "l")
                        } else if(foodItem.categoryName == "Food") {
                            Text(text = "/kg")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "₹${(foodItem.price * 3).roundToInt()}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.weight(.1f))

                    }


                    /*Spacer(modifier = Modifier.height(12.dp))
                    Row {
    //                    Text(text = "1 Unit")
    //                    Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "₹${foodItem.price}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "/kg")
                    }
                    Spacer(modifier = Modifier.height(4.dp))*/
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewCartScreen() {
    FoodCartTheme {
        CartItem(foodItem = FoodItemEntity(0, "Carrot is very healy for our", "", 0.0, "", 1), {})
    }
}