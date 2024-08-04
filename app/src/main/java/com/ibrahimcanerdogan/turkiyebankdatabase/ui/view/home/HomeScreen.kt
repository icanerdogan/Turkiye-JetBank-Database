package com.ibrahimcanerdogan.turkiyebankdatabase.ui.view.home

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.ibrahimcanerdogan.turkiyebankdatabase.R
import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankData
import com.ibrahimcanerdogan.turkiyebankdatabase.data.model.BankDataItem
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.navigation.AppScreen
import com.ibrahimcanerdogan.turkiyebankdatabase.ui.viewmodel.HomeViewModel
import com.ibrahimcanerdogan.turkiyebankdatabase.util.ErrorImage
import com.ibrahimcanerdogan.turkiyebankdatabase.util.LoadingAnimation
import com.ibrahimcanerdogan.turkiyebankdatabase.util.NoDataImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val gson = Gson()

    BackHandler {
        (context as? android.app.Activity)?.finish()
    }

    // States
    val state = viewModel.homeState.value
    val filteredBankDataList = viewModel.filteredBankDataList.value
    val searchQuery = remember { mutableStateOf("") }
    // Nav Controller
    val currentNavController = rememberUpdatedState(navController)

    val flagPainter: Painter = painterResource(id = R.drawable.flag)
    val flagEngPainter: Painter = painterResource(id = R.drawable.flageng)

    Scaffold(
        topBar = {
            Column {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { newValue ->
                        searchQuery.value = newValue
                        viewModel.filterBankDataList(newValue)
                    },
                    label = { Text(stringResource(R.string.search_by_city)) },
                    modifier = Modifier
                        .padding(vertical = 30.dp, horizontal = 20.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = flagEngPainter,
                        contentDescription = "Change language to English",
                        modifier = Modifier
                            .clickable { changeLanguage(context, currentNavController, "en") }
                            .size(100.dp)

                    )
                    Image(
                        painter = flagPainter,
                        contentDescription = "Change language to Turkish",
                        modifier = Modifier
                            .clickable { changeLanguage(context, currentNavController, "tr") }
                            .size(100.dp)
                    )
                }
            }
        },
        content = { paddingValue ->

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            } else {
                if (!state.errorMessage.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorImage()
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                if (filteredBankDataList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(!state.isLoading) {
                                    NoDataImage()
                                }
                            }
                        }
                    }
                } else {
                    items(filteredBankDataList) { bankItem ->
                        BankDataCard(bankItem = bankItem) {
                            val bankDataJson = gson.toJson(bankItem)
                            val encodedBankDataJson = URLEncoder.encode(bankDataJson, StandardCharsets.UTF_8.toString())
                            navController.navigate("${AppScreen.DETAIL_SCREEN.name}/$encodedBankDataJson")
                        }
                    }
                }
            }
        }
    )

}

@Preview
@Composable
fun BankDataCard(
    bankItem: BankDataItem = BankDataItem(),
    onClickToCard: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clickable(onClick = onClickToCard)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            if (bankItem.bankBranch.isNullOrEmpty()) {
                BankDataCardItem(Icons.Default.AccountBalance, "${bankItem.bankDistrict} ŞUBESİ/${bankItem.bankCity}")
            } else {
                BankDataCardItem(Icons.Default.AccountBalance, bankItem.bankBranch)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "${bankItem.bankAddress}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )
            )
        }
    }
}

@Composable
fun BankDataCardItem(
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
                fontWeight = FontWeight.Medium
            )
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}

private fun changeLanguage(
    context: Context,
    currentNavController: State<NavController>,
    language: String
) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics);

    // Force UI recreation
    val currentDestination = currentNavController.value.currentDestination?.id
    currentNavController.value.popBackStack()
    currentDestination?.let { currentNavController.value.navigate(it) }
}