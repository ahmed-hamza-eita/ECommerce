package com.hamza.ecommerce.data.datasource.networking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hamza.ecommerce.data.models.GenericResponse
import com.hamza.ecommerce.data.models.auth.RegisterRequestModel
import com.hamza.ecommerce.data.models.auth.RegisterResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.Reader


interface CloudFunctionAPI {

    @POST("registerUser")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequestModel
    ): Response<GenericResponse<RegisterResponseModel>>

}

fun handleErrorResponse(response: Reader): String {
    val errorResponse = try {
        Gson().fromJson(
            response, GenericResponse::class.java
        ).message
    } catch (e: Exception) {
        e.message
    }
    return errorResponse ?: ""
}