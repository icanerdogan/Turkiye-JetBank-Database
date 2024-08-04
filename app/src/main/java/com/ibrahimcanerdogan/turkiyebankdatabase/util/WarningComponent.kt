package com.ibrahimcanerdogan.turkiyebankdatabase.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ibrahimcanerdogan.turkiyebankdatabase.R
import com.ibrahimcanerdogan.turkiyebankdatabase.common.isNetworkAvailable
import kotlinx.coroutines.flow.Flow

@Composable
fun ErrorImage() {
    val errorPainter: Painter = painterResource(id = R.drawable.error)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = errorPainter,
            contentDescription = "Error Image",
            modifier = Modifier.fillMaxSize().padding(10.dp)
        )
    }
}

@Composable
fun NoDataImage() {

    val noDataPainter: Painter = painterResource(id = R.drawable.nodata)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = noDataPainter,
            contentDescription = "No Data Image",
            modifier = Modifier.fillMaxSize().padding(10.dp)
        )
    }
}

@Composable
fun NoInternetDialog(internetConnectionFlow: Flow<Boolean>) {
    val context = LocalContext.current
    val isConnected by internetConnectionFlow.collectAsState(initial = isNetworkAvailable(context))
    val showDialog = remember { mutableStateOf(!isConnected) }

    if (!isConnected) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("İnternet Bağlantısı Yok") },
            text = { Text("Lütfen internet bağlantınızı kontrol edin ve tekrar deneyin.") },
            confirmButton = {
                TextButton(onClick = {
                    if (isNetworkAvailable(context)) {
                        showDialog.value = false
                    }
                }) {
                    Text("Tekrar Deneyin")
                }
            }
        )
    }
}

@Composable
fun LoadingAnimation() {
    var isPlaying by remember {
        mutableStateOf(true)
    }

    var speed by remember {
        mutableFloatStateOf(1f)
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.bankloading)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center
        )
    }
}
