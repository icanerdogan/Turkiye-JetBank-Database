package com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

@Composable
fun BankDetailCard(bankData: BankDataItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.city) + " ${bankData.bankCity}")
            Text(text = stringResource(R.string.district) + " ${bankData.bankDistrict}")
            Text(text = stringResource(R.string.bank_branch) + " ${bankData.bankBranch}")
            Text(text = stringResource(R.string.bank_type) + " ${bankData.bankType}")
            Text(text = stringResource(R.string.bank_code) + " ${bankData.bankCode}")
            Text(text = stringResource(R.string.address_name) + " ${bankData.bankAddressName}")
            Text(text = stringResource(R.string.address) + " ${bankData.bankAddress}")
            Text(text = stringResource(R.string.postal_code) + " ${bankData.bankPostalCode}")
            Text(text = stringResource(R.string.on_off_line) + " ${bankData.bankOffLine}")
            Text(text = stringResource(R.string.on_off_site) + " ${bankData.bankOffSite}")
            Text(text = stringResource(R.string.region_coordinator) + " ${bankData.bankCoordinate}")
            Text(text = stringResource(R.string.nearest_atm) + " ${bankData.bankAtm}")
        }
    }
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