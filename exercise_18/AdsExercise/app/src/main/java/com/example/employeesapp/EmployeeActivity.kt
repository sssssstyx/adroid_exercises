package com.example.employeesapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_employee.*
import org.json.JSONObject
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAdCallback

class EmployeeActivity : AppCompatActivity() {

    private lateinit var rewardedAd: RewardedAd

    // EmployeeActivity's onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        rewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
                button.visibility = View.VISIBLE
            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        button.setOnClickListener {
            if (rewardedAd.isLoaded) {
//                val activityContext: Activity = ...
                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        // Ad closed.
                    }
                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        // User earned reward.
                    }
                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        // Ad failed to display.
                    }
                }
                rewardedAd.show(this, adCallback)
            }
            else {
                Log.d("TAG", "The rewarded ad wasn't loaded yet.")
            }
        }

        // get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val employeeString = bundle!!.getString("employee")
            val employee = JSONObject(employeeString)
            // val name = employee["first_name"]
            // modify here to display other employee's data too!
            // Toast.makeText(this, "Name is $name",Toast.LENGTH_LONG).show()
            nameTextView.text = employee["last_name"].toString() + " " + employee["first_name"].toString()
            genderTextView.text = employee["gender"].toString()
            emailTextView.text = employee["email"].toString()
            phoneTextView.text = employee["phone"].toString()
            addressTextView.text = employee["address"].toString()
            Glide.with(avatarImageView.context).load(employee["image"]).transform(CircleCrop()).into(avatarImageView)
        }
    }

}
