package com.ibrahimcanerdogan.turkiyebankdatabase.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {
    fun logDetailPageEvent(city: String?) {
        firebaseAnalytics.logEvent("event_detail_screen") {
            param("param_detail_city", city ?: "null")
        }
    }
}