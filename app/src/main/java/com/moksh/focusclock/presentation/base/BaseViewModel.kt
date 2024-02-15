package com.moksh.focusclock.presentation.base

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
) : ViewModel() {

    var mRewardedAd: RewardedAd? = null
    private val AD_UNIT_ID = "ca-app-pub-4609289273562170/3610422382"

    fun loadRewarded(context: Context) {
        RewardedAd.load(context,
            AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                    Log.e("adError: ",adError.message)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    mRewardedAd = rewardedAd
                }
            })
    }

    fun showRewarded(context: Context, onAdDismissed: () -> Unit, onRewardEarned: () -> Unit) {
        val activity = context as Activity

        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: com.google.android.gms.ads.AdError) {
                    mRewardedAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mRewardedAd = null
                    loadRewarded(context)
                    onAdDismissed()
                }
            }
            mRewardedAd?.show(activity, object : OnUserEarnedRewardListener {
                override fun onUserEarnedReward(rewardItem: RewardItem) {
                    onRewardEarned()
                }
            })
        }
    }

    fun removeRewarded() {
        mRewardedAd?.fullScreenContentCallback = null
        mRewardedAd = null
    }
}