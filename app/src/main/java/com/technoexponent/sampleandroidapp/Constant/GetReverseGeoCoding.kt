package com.technoexponent.sampleandroidapp.Constant

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by mobile08 on 2/6/2018.
 */

class GetReverseGeoCoding {

    private var Address1 = ""
    private var Address2 = ""
    private var City = ""
    private var State = ""
    private var Country = ""
    private var County = ""
    private var PIN = ""

    fun getAddress(lat: String, lon: String) {
        Address1 = ""
        Address2 = ""
        City = ""
        State = ""
        Country = ""
        County = ""
        PIN = ""

        try {
            /*   JSONObject jsonObj = JsonParser.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + ","
                    + lon + "&sensor=true");*/

            val jsonObj = JsonParser.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + ","
                    + lon + "&sensor=true")


            /*   JSONObject jsonObj = JsonParser.getJSONfromURL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + ","
                    + lon + "&key=AIzaSyDEAyjVwwCc_ZquOOfFWuzO0bJYOjeY3YU");*/

            val Status = jsonObj!!.getString("status")
            if (Status.equals("OK", ignoreCase = true)) {
                val Results = jsonObj.getJSONArray("results")
                val zero = Results.getJSONObject(0)
                val address_components = zero.getJSONArray("address_components")

                for (i in 0 until address_components.length()) {
                    val zero2 = address_components.getJSONObject(i)
                    val long_name = zero2.getString("long_name")
                    val mtypes = zero2.getJSONArray("types")
                    val Type = mtypes.getString(0)

                    if (long_name != null) {
                        if (TextUtils.isEmpty(long_name) == false || long_name != null || long_name.length > 0 || long_name !== "") {
                            if (Type.equals("street_number", ignoreCase = true)) {
                                Address1 = "$long_name "
                            } else if (Type.equals("route", ignoreCase = true)) {
                                Address1 = Address1 + long_name
                            } else if (Type.equals("sublocality", ignoreCase = true)) {
                                Address2 = long_name
                            } else if (Type.equals("locality", ignoreCase = true)) {
                                // Address2 = Address2 + long_name + ", ";
                                City = long_name
                                break
                            } else if (Type.equals("administrative_area_level_2", ignoreCase = true)) {
                                County = long_name
                            } else if (Type.equals("administrative_area_level_1", ignoreCase = true)) {
                                State = long_name
                            } else if (Type.equals("country", ignoreCase = true)) {
                                Country = long_name
                            } else if (Type.equals("postal_code", ignoreCase = true)) {
                                PIN = long_name
                            }
                        }
                    }

                    // JSONArray mtypes = zero2.getJSONArray("types");
                    // String Type = mtypes.getString(0);
                    // Log.e(Type,long_name);
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getAddress1(lat: String, lon: String): String {
        getAddress(lat, lon)
        return Address1

    }

    fun getAddress2(lat: String, lon: String): String {
        getAddress(lat, lon)
        return Address2

    }

    fun getCity(lat: String, lon: String): String {
        getAddress(lat, lon)
        return City

    }

    fun getState(lat: String, lon: String): String {
        getAddress(lat, lon)
        return State

    }

    fun getCountry(lat: String, lon: String): String {
        getAddress(lat, lon)
        return Country

    }

    fun getCounty(lat: String, lon: String): String {
        getAddress(lat, lon)
        return County

    }

    fun getPIN(lat: String, lon: String): String {
        getAddress(lat, lon)
        return PIN

    }

    fun getSubLocality(lat: String, lon: String): String {
        getAddress(lat, lon)
        return Address2

    }

}