package com.shai.syncsms.data.network

import com.shai.syncsms.data.entity.Sms
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsApi {
    @POST("save_sms")
    suspend fun pushSmsList(@Body smsList: List<Sms>): Response<List<Long>>
}