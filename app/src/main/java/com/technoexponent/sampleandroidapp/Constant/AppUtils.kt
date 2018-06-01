package com.technoexponent.sampleandroidapp.Constant

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils

/**
 * Created by Suman on 6/13/2017.
 */

class AppUtils {

    object LocationConstants {
        val SUCCESS_RESULT = 0

        val FAILURE_RESULT = 1

        val PACKAGE_NAME = "com.sample.sishin.maplocation"

        val RECEIVER = "$PACKAGE_NAME.RECEIVER"

        val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"

        val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"
        val LOCATION_DATA_AREA = "$PACKAGE_NAME.LOCATION_DATA_AREA"
        val LOCATION_DATA_CITY = "$PACKAGE_NAME.LOCATION_DATA_CITY"
        val LOCATION_DATA_STREET = "$PACKAGE_NAME.LOCATION_DATA_STREET"


    }

    companion object {

        fun hasLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        fun isLocationEnabled(context: Context): Boolean {
            var locationMode = 0
            val locationProviders: String

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)

                } catch (e: Settings.SettingNotFoundException) {
                    e.printStackTrace()
                }

                return locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                locationProviders = Settings.Secure.getString(context.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
                return !TextUtils.isEmpty(locationProviders)
            }
        }

        fun isAppIsInBackground(context: Context): Boolean {
            var isInBackground = true
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                val runningProcesses = am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } else {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo.packageName == context.packageName) {
                    isInBackground = false
                }
            }

            return isInBackground
        }
    }
}
