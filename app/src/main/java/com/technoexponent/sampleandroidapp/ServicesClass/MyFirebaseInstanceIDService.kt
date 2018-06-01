package com.technoexponent.sampleandroidapp.ServicesClass

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


/**
 * Created by technoexponent on 07-Dec-17.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // Store Refresh token into SharedPreferenceHelper
       // SharedPreferenceHelper.addStringInSharedPreference(this@MyFirebaseInstanceIDService, Constant.SHARE_REFRESH_TOKEN, Constant.REFRESH_TOKEN, refreshedToken)

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken)
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // Add custom implementation, as needed.
    }

    companion object {
        private val TAG = "FirebaseIDService"
    }
}