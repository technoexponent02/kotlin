package com.technoexponent.sampleandroidapp.Constant

import android.util.Log

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by Suman on 8/14/2017.
 */

object JsonParser {

    fun getJSONfromURL(url: String): JSONObject? {

        // initialize
        var `is`: InputStream? = null
        var result = ""
        var jObject: JSONObject? = null

        // http post
        try {
            val httpclient = DefaultHttpClient()
            val httppost = HttpPost(url)
            val response = httpclient.execute(httppost)
            val entity = response.entity
            `is` = entity.content

        } catch (e: Exception) {
            Log.e("log_tag", "Error in http connection " + e.toString())
        }

        // convert response to string
        try {
            val reader = BufferedReader(InputStreamReader(`is`!!, "iso-8859-1"), 8)
            val sb = StringBuilder()
            var line: String? = null
            while ((line = reader.readLine()) != null) {
                sb.append(line!! + "\n")
            }
            `is`.close()
            result = sb.toString()
        } catch (e: Exception) {
            Log.e("log_tag", "Error converting result " + e.toString())
        }

        // try parse the string to a JSON object
        try {
            jObject = JSONObject(result)
        } catch (e: JSONException) {
            Log.e("log_tag", "Error parsing data " + e.toString())
        }

        return jObject
    }
}
