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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

@ExperimentalMaterial3Api
@Composable
fun FavouriteScreen(onBack: () -> Unit) {

    val viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
    val fav by viewModel.favData.collectAsStateWithLifecycle()

    Scaffold(containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "favourite",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                },
                modifier = Modifier,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                onBack()
                            }
                    )
                },
            )
        }) {
        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .animateContentSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(fav.isEmpty()){
                item {
                    Text(text = "Add Something to Favourites.", style = MaterialTheme.typography.titleLarge)
                }

            }
            items(fav, key = {it.id}, ) { food: FoodItemEntity ->
                FavFoodItem(foodItem = food, onFavClick = viewModel::onFavClick)
            }
        }
    }
}

@Composable
private fun FavFoodItem(foodItem: FoodItemEntity, onFavClick: (FoodItemEntity) -> Unit) {
    val color by animateColorAsState(
        targetValue = if (foodItem.isFav) Color.Red else Color.White,
        label = ""
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
            Column(modifier = Modifier .weight(.3f)) {
                Text(
                    text = foodItem.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Text(text = "1 Unit")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "₹${foodItem.price}", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =  Modifier.weight(.18f)) {
                Icon(
                    modifier = Modifier
                        //   .align(Alignment.TopEnd)
                        .padding(2.dp)
                        .clickable { onFavClick(foodItem) },
                    imageVector = Icons.Filled.Favorite,
                    tint = color,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.height(12.dp))

                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Orange, Color.White
                    ),
                    modifier = Modifier.size(36.dp),
                    onClick = { onFavClick(foodItem) },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewFavouriteScreen() {
    FoodCartTheme {
        FavouriteScreen(){}
    }
}