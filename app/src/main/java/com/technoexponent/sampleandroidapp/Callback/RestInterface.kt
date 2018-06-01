package com.technoexponent.sampleandroidapp.Callback

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RestInterface {


    @FormUrlEncoded
    @POST("")
    fun getRegistration(@Field("countryCode") countryCode: String,
                        @Field("mobile") mobile: String,
                        @Field("userType") userType: String)
            : Call<ResponseBody>




}

