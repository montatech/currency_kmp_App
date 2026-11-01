package org.example.project.peresentation.composeScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import currencykmpapp.composeapp.generated.resources.Res
import currencykmpapp.composeapp.generated.resources.backArrow
import currencykmpapp.composeapp.generated.resources.menu
import currencykmpapp.composeapp.generated.resources.refresh
import currencykmpapp.composeapp.generated.resources.search
import org.example.project.network.model.CurrencyDtoItem
import org.example.project.peresentation.viewModel.MainViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import kotlin.math.roundToLong


@Composable
fun HomeScreen(
    onSearchClicked: () -> Unit
) {
    val viewModel = koinInject<MainViewModel>()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
                    .background(color = Color.White)
                    .padding(end = 10.dp, start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = { }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.menu),
                        contentDescription = "menu"
                    )
                }
                Row {

                    IconButton(
                        modifier = Modifier.size(28.dp),

                        onClick = { onSearchClicked() }
                    ) {
                        Image(
                            painter = painterResource(resource = Res.drawable.search),
                            contentDescription = "search"
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        modifier = Modifier.size(25.dp),
                        onClick = { viewModel.refresh() }
                    ) {
                        Image(
                            painter = painterResource(resource = Res.drawable.refresh),
                            contentDescription = "refresh"
                        )
                    }
                }


            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffFbfbFb))
                .padding(it)
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center), color = Color.DarkGray)
            } else if (viewModel.errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        viewModel.errorMessage ?: "error",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = { viewModel.refresh() }
                    ) {
                        Image(
                            painter = painterResource(resource = Res.drawable.refresh),
                            contentDescription = "refresh"
                        )
                    }
                }
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            text = "Crypto Market OverView",
                            fontSize = 23.sp

                        )
                    }
                    items(viewModel.currency) { item ->
                        Spacer(modifier = Modifier.height(10.dp))
                        CurrencyListShow(item)
                    }
                }
            }
        }
    }

}

@Composable
fun SearchScreen(
    onBack: () -> Unit
) {
    val viewModel = koinInject<MainViewModel>()
    var text by remember { mutableStateOf("") }


    Scaffold(
        Modifier.systemBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
                    .height(57.dp)
                    .background(color = Color.White)
                    .padding(end = 10.dp, start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = {
                        onBack()
                    }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.backArrow),
                        contentDescription = "back"
                    )
                }

                TextField(
                    modifier = Modifier.fillMaxWidth(0.7f).padding(0.dp).height(45.dp),
                    value = text,
                    onValueChange = {
                        if (text.length < 40){
                            text = it
                        }
                    },
                    trailingIcon = {
                        Image(
                            modifier = Modifier
                                .size(18.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                    onClick = {
                                        viewModel.load(text)

                                    }
                                ),
                            painter = painterResource(resource = Res.drawable.search),
                            contentDescription = "search"
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    textStyle = TextStyle(fontSize = 13.sp),
                )




                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = { viewModel.refresh() }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.refresh),
                        contentDescription = "refresh"
                    )
                }
            }


        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffFbfbFb))
                .padding(it)
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center), color = Color.DarkGray)
            } else if (viewModel.errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        viewModel.errorMessage ?: "error",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = { viewModel.refresh() }
                    ) {
                        Image(
                            painter = painterResource(resource = Res.drawable.refresh),
                            contentDescription = "refresh"
                        )
                    }
                }
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            text = "Crypto Market OverView",
                            fontSize = 23.sp

                        )
                    }
                    items(viewModel.currency) { item ->
                        Spacer(modifier = Modifier.height(10.dp))
                        CurrencyListShow(item)
                    }
                }
            }
        }
    }

}


@Composable
fun CurrencyListShow(
    currency: CurrencyDtoItem
) {
    var state by remember { mutableStateOf(false) }
    val animatedHeight by animateDpAsState(
        targetValue = if (state) 80.dp else 70.dp,
        animationSpec = tween(durationMillis = 600),
        label = "heightAnimation"
    )



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedHeight)
            .padding(end = 10.dp, start = 10.dp)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = {
                    state = !state
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {

            val image = rememberImagePainter(currency.image)
            Image(
                modifier = Modifier
                    .size(65.dp)
                    .padding(10.dp)
                    .background(color = Color.Transparent, shape = CircleShape),
                painter = image,
                contentDescription = currency.name
            )


            Column(
                modifier = Modifier.fillMaxHeight()

            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = currency.name,
                    fontSize = 20.sp

                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Text(
                    currency.symbol,
                    fontSize = 20.sp

                )
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight()

        ) {

            Text(
                text = "${formatWithCommas(currency.currentPrice)}$",
                fontSize = 20.sp
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                "${currency.priceChangePercentage24h}%",
                color = if (currency.priceChangePercentage24h > 0) Color(0xff0A800D) else Color.Red,
                fontSize = 20.sp

            )

        }


    }
    if (state) {

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(bottom = 5.dp)
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "DETAIL SCREEN",
                fontSize = 23.sp

            )
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffF7F7F7)
                )
            ) {
                Text(
                    text = "More",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }


        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            SparklineChart(
                prices = currency.SparklineIn7d.price, // هرچی بخوای کمش کن
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                lineColor = if (currency.priceChangePercentage24h >= 0) Color(0xFF4CAF50) else Color(
                    0xFFF44336
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(bottom = 5.dp)
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Market Cap",
                fontSize = 20.sp
            )
            Text(
                text = formatLongWithCommas(currency.marketCap),
                fontSize = 20.sp

            )

        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(top = 5.dp, bottom = 5.dp)
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Volume",
                fontSize = 20.sp
            )
            Text(
                text = formatLongWithCommas(currency.totalVolume.roundToLong()),
                fontSize = 20.sp

            )

        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(top = 5.dp, bottom = 5.dp)
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Supply",
                fontSize = 20.sp
            )
            Text(
                text = if (currency.totalSupply == null) "no data" else formatLongWithCommas(
                    currency.totalSupply.roundToLong()
                ),
                fontSize = 20.sp

            )

        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(top = 5.dp, bottom = 5.dp)
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Max Supply",
                fontSize = 20.sp
            )
            Text(
                text = if (currency.maxSupply == null) "no data" else formatLongWithCommas(
                    currency.maxSupply.roundToLong()
                ),
                fontSize = 20.sp

            )

        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
        )
    } else {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
        )

    }
}

@Composable
fun SparklineChart(
    prices: List<Double>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Gray
) {
    if (prices.isEmpty()) return

    val maxPrice = prices.maxOrNull() ?: 1.0
    val minPrice = prices.minOrNull() ?: 0.0
    val priceRange = (maxPrice - minPrice).takeIf { it != 0.0 } ?: 1.0

    Canvas(
        modifier = modifier
            .background(Color.Transparent)
            .padding(3.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        val path = Path()
        val stepX = size.width / (prices.size - 1)

        prices.forEachIndexed { index, price ->
            val x = index * stepX
            val normalizedY = 1f - ((price - minPrice) / priceRange).toFloat()
            val y = normalizedY * size.height

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )
    }
}

fun formatWithCommas(number: Double): String {
    val parts = number.toString().split(".")
    val integerPart = parts[0]
    val decimalPart = if (parts.size > 1) parts[1] else ""

    val formattedInteger = integerPart.reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()

    return if (decimalPart.isNotEmpty()) {
        "$formattedInteger.$decimalPart"
    } else {
        formattedInteger
    }
}

fun formatLongWithCommas(number: Long): String {
    return number.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}






