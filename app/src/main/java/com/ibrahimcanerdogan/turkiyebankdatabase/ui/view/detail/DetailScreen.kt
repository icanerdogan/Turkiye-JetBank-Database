package com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.ibrahimcanerdogan.turkiyebankdatabase.R
import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankDataItem
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.viewmodel.DetailViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(bankDataJson: String) {
    val viewModel: DetailViewModel = hiltViewModel()
    val context = LocalContext.current

    // Bank Data
    val gson = Gson()
    val bankJson = URLDecoder.decode(bankDataJson, StandardCharsets.UTF_8.toString())
    val bankData = gson.fromJson(bankJson, BankDataItem::class.java)

    // Log Firebase Event
    viewModel.logDetailPageEvent(bankData.bankCity ?: "")


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //https://developers.google.com/maps/documentation/urls/android-intents#kotlin_2
                startMapIntent(bankData, context)
            }) {
                Icon(Icons.Filled.LocationOn, contentDescription = "Navigate")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            BankDetailCard(bankData = bankData)
        }
    }
}

@Preview
@Composable
fun BankDetailCard(bankData: BankDataItem = BankDataItem()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(30.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.bank_code),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = "${bankData.bankCode}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = stringResource(R.string.postal_code),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = "${bankData.bankPostalCode}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            BankCardDetailItem(Icons.Default.LocationCity, "${bankData.bankCity} / ${bankData.bankDistrict}")
            BankCardDetailItem(Icons.Default.Store, "${bankData.bankAddressName}")
            BankCardDetailItem(Icons.Default.LocationOn, "${bankData.bankAddress}")
            BankCardDetailItem(Icons.Default.Public, "${bankData.bankCoordinate}")
            BankCardDetailItem(Icons.Default.Atm, "${bankData.bankAtm}")

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun BankCardDetailItem(
    bankItemVector: ImageVector,
    bankItemText: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = bankItemVector,
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${bankItemText}",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}

private fun startMapIntent(
    bankData: BankDataItem,
    context: Context
) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${bankData.bankAddress}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}