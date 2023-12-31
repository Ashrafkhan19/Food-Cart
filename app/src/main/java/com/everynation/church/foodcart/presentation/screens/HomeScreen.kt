package com.everynation.church.foodcart.presentation.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.everynation.church.foodcart.data.ShoppingDataModel
import com.everynation.church.foodcart.data.local.FoodItemEntity
import com.everynation.church.foodcart.data.shoppingData
import com.everynation.church.foodcart.presentation.MainViewModel
import com.everynation.church.foodcart.ui.theme.FoodCartTheme
import com.everynation.church.foodcart.ui.theme.Orange
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFavNavClick: () -> Unit,
    onCartNavClick: () -> Unit,
) {
    val viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
    val data by viewModel.data.collectAsStateWithLifecycle()
    val cartCount by viewModel.cartData.collectAsStateWithLifecycle()
    val favCount by viewModel.favData.collectAsStateWithLifecycle()
    Scaffold(
        //containerColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "My Store", color = Color.Black) },
                modifier = Modifier.background(
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFF5722), Color(0xFFE6DA75)),
                    )
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                },
                actions = {
                    BadgedBox(badge = {
                        if(favCount.isNotEmpty()) {
                        Badge(modifier = Modifier.offset((-10).dp, 4.dp)) {

                                Text(text = favCount.size.toString())
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                onFavNavClick()
                            }

                        )
                    }


                    Spacer(modifier = Modifier.width(8.dp))
                    BadgedBox(badge = {
                        Badge(modifier = Modifier.offset((-10).dp, 4.dp)) {
                            Text(text = cartCount.size.toString())
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                onCartNavClick()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }) {
        Box(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding()

                )
                .fillMaxSize(), contentAlignment = Alignment.Center,
        ) {



            if(data.isEmpty()){
                CircularProgressIndicator()
            } else {
                HomePage(
                    data,
                    onItemClick = { _, _ -> },
                    onFavClick = viewModel::onFavClick,
                    saveToCart = viewModel::saveToCart
                )

                CategoriesDropDown(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)

                        .padding(bottom = it.calculateBottomPadding() + 12.dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp),
                    data.keys
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesDropDown(modifier: Modifier = Modifier, keys: Set<String>) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Box(modifier = modifier
        // .padding(horizontal = 64.dp)
        .clickable {
            isExpanded = !isExpanded
        }, contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = !isExpanded) {
            Row {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Categories")
            }
        }
        
        AnimatedVisibility(visible = isExpanded) {
            FilledIconButton(onClick = {
                isExpanded = false
            }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription ="")
            }
        }
        
        DropdownMenu(
            properties = PopupProperties(usePlatformDefaultWidth = true),
            modifier = Modifier
                .wrapContentWidth()
                //.fillMaxWidth()
                .align(Alignment.Center),
            //offset = DpOffset(x = (0).dp, y = (-200).dp),
            expanded = isExpanded, onDismissRequest = { /*TODO*/ }) {
            keys.forEach {
                DropdownMenuItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = { Text(text = it) }, onClick = { /*TODO*/ })
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoriesDropDown() {
    FoodCartTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CategoriesDropDown(
                keys = Gson().fromJson(
                    shoppingData,
                    ShoppingDataModel::class.java
                ).categories.map { it.name }.toSet()
            )
        }
    }
}

@Composable
fun HomePage(
    data: Map<String, List<FoodItemEntity>>,
    onItemClick: (ShoppingDataModel.Category, Boolean) -> Unit,
    onFavClick: (FoodItemEntity) -> Unit,
    saveToCart: (FoodItemEntity) -> Unit,
) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        data.forEach {
            sectionItem(category = it, onItemClick = onItemClick, onFavClick = onFavClick, saveToCart = saveToCart)
        }


    }
}


private fun LazyListScope.sectionItem(
    category: Map.Entry<String, List<FoodItemEntity>>,
    onItemClick: (ShoppingDataModel.Category, Boolean) -> Unit,
    onFavClick: (FoodItemEntity) -> Unit,
    saveToCart: (FoodItemEntity) -> Unit,
) {
    item {
        Column {
            var isOpen by rememberSaveable {
                mutableStateOf(false)
            }
            val rotate by animateFloatAsState(
                targetValue = if (isOpen) 180f else 0f,
                label = "",
            )
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                isOpen = !isOpen

            }) {
                Text(text = category.key, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    modifier = Modifier.rotate(rotate)
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = !isOpen) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {

                    items(category.value) { foodItem ->
                        FoodItem(foodItem, onFavClick = onFavClick, saveToCart = saveToCart)
                    }

                }
            }

        }
    }
}

@Composable
private fun FoodItem(
    foodItem: FoodItemEntity,
                     onFavClick: (FoodItemEntity) -> Unit,
    saveToCart: (FoodItemEntity) -> Unit,
) {
    val color by animateColorAsState(
        targetValue = if (foodItem.isFav) Color.Red else Color.White,
        label = ""
    )

    val scope = rememberCoroutineScope()

    val scaleX = remember {
        Animatable(1f)
    }

    Card {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 142.dp)
        ) {
            Column(
                modifier = Modifier, horizontalAlignment = Alignment.Start
            ) {
                AsyncImage(
                    model = foodItem.icon,
                    contentDescription = null,
                    modifier = Modifier.size(84.dp)
                )
                Text(text = foodItem.name, style = MaterialTheme.typography.titleMedium)
                Row(
                    //modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "₹${foodItem.price.roundToInt()}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    if (foodItem.categoryName == "Beverages") {
                        Text(text = " L")
                    } else if (foodItem.categoryName == "Food") {
                        Text(text = "/kg")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    //Spacer(modifier = Modifier.width(16.dp))
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Orange, Color.White
                        ),
                        modifier = Modifier.size(36.dp),
                        onClick = { saveToCart(foodItem) },
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(imageVector = if(!foodItem.isOnCart) Icons.Default.Add else Icons.Default.Check, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(1.dp))
                }
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(2.dp)
                    .clickable {
                        scope.launch {
                            onFavClick(foodItem)
                            scaleX.animateTo(1.5f)
                            delay(100L)
                            scaleX.animateTo(1.0f)
                        }
                    }
                    .graphicsLayer {
                        this.scaleX = scaleX.value
                        this.scaleY = scaleX.value
                    }
                ,
                imageVector = Icons.Filled.Favorite,
                tint = color,
                contentDescription = "",
            )
        }
    }
}