package com.technoexponent.taxiapp.passenger.Constant

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by technoexponent on 11-Dec-17.
 */

object SharedPreferenceHelper {
    var sharedPreferences: SharedPreferences? = null
    var sharedPreferencesEditor: SharedPreferences.Editor? = null

    fun openSharedPrefernceInEditMode(context: Context?, sharedPrefernceName: String?) {
        sharedPreferences = context?.getSharedPreferences(sharedPrefernceName, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences?.edit()
    }

    fun addStringInSharedPreference(context: Context?, sharedPreferenceName: String?, Key: String?, value: String?) {
        openSharedPrefernceInEditMode(context, sharedPreferenceName)
        sharedPreferencesEditor?.putString(Key, value)?.commit()
    }

    fun getStringFromSharedPrefernce(context: Context?, sharedPreferenceName: String?, key: String?): String {
        sharedPreferences = context?.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences!!.getString(key, "")
    }



    /**
     * Device Id details sharedPreference
     *
     * @param applicationContext
     * @param sharedPreferenceName
     * @param deviceId
     */
    fun deviceIdSharedPreference(applicationContext: Context, sharedPreferenceName: String, deviceId: String) {
        sharedPreferences = applicationContext.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences?.edit()

        sharedPreferencesEditor?.putString(Constant.DEVICE_ID, deviceId)

        sharedPreferencesEditor?.commit()
    }



    /**
     * Erase all SharedPreference data
     */
    fun deleteAllSharedPreference(context: Context) {
        //remove all your prefs
       context.getSharedPreferences(Constant.SHARE_LOGIN_DETAILS, 0).edit().clear().commit()
        //context.getSharedPreferences(Constant.SHARE_LAT_LNG, 0).edit().clear().commit()
    }



    /**
     * Store login user details
     */


    fun loginSharedPreference(applicationContext: Context, sharedPreferenceName: String, id: String? ,
                              firstName: String?,  lastName: String?,  gender: String, addressLine1: String, addressLine2: String,
                              city: String, state: String, zipcode: String?,email:String?,countryCode:String?,
                              mobile:String? ,balance:String?,userType:String?,loginType:String?,status:String?,
                              mAuthorization:String?,userPic:String?,profile_img_link:String?) {
        sharedPreferences = applicationContext.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences?.edit()
        sharedPreferencesEditor?.let {
            it.putString(Constant.LOGIN_SHARE_USER_ID, id)
            it.putString(Constant.LOGIN_SHARE_USER_FIRST_NAME, firstName)
            it.putString(Constant.LOGIN_SHARE_USER_LAST_NAME, lastName)
            it.putString(Constant.LOGIN_SHARE_USER_GENDER, gender)
            it.putString(Constant.LOGIN_SHARE_USER_ADDRESSLINE1, addressLine1)
            it.putString(Constant.LOGIN_SHARE_USER_ADDRESSLINE2, addressLine2)
            it.putString(Constant.LOGIN_SHARE_USER_CITY, city)
            it.putString(Constant.LOGIN_SHARE_USER_STATE, state)
            it.putString(Constant.LOGIN_SHARE_USER_ZIPCODE, zipcode)
            it.putString(Constant.LOGIN_SHARE_USER_EMAIL, email)
            it.putString(Constant.LOGIN_SHARE_USER_COUNTRYCODE, countryCode)
            it.putString(Constant.LOGIN_SHARE_USER_MOBILENO, mobile)
            it.putString(Constant.LOGIN_SHARE_USER_BALANCE, balance)
            it.putString(Constant.LOGIN_SHARE_USER_TYPE, userType)
            it.putString(Constant.LOGIN_SHARE_USER_LOGIN_TYPE, loginType)
            it.putString(Constant.LOGIN_SHARE_USER_STATUS, status)
            it.putString(Constant.LOGIN_SHARE_USER_ACCESS_TOKEN, mAuthorization)
            it.putString(Constant.LOGIN_SHARE_USER_PIC, userPic)
            it.putString(Constant.PROFILE_IMAGE_LINK, profile_img_link)

            it.commit()
        }
    }



}





