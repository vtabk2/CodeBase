package com.codebasetemplate.required.adjust

import com.core.ads.admob.AdjustAnalytics
import javax.inject.Singleton

@Singleton
class AdjustTracking: AdjustAnalytics {
    override fun trackRevenueNetwork(
        adUnitId: String,
        adValueMicros: Long,
        adValueCurrencyCode: String
    ) {
//        val adRevenue = AdjustAdRevenue("admob_sdk")
//        adRevenue.setRevenue(
//            adValueMicros / 1_000_000.0,
//            adValueCurrencyCode
//        )
//        adRevenue.setAdRevenueNetwork(adUnitId) // ad unit ID
//        Adjust.trackAdRevenue(adRevenue)
    }
}