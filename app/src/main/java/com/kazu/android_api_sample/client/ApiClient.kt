package com.kazu.android_api_sample.client

import android.util.Log
import com.kazu.android_api_sample.common.Constants
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ApiClient {
    private val client = OkHttpClient()

    fun get(url: String, id:String, callback: (String?, String?) -> Unit) {
        Log.d("ApiClient", "GET ${Constants.USERS_API}$id")
        val getUrl = "$url${Constants.USERS_API}$id"
        println("url : $getUrl")
        val request: Request = Request.Builder()
            .url(getUrl)
            .get()
            .build()

        client.newCall(request).enqueue(responseCallback(callback))
    }

    fun getAllUser(url: String, callback: (String?, String?) -> Unit) {
        Log.d("ApiClient", "GET ${Constants.USERS_API}")
        val getUrl = "$url${Constants.USERS_API}"
        println("url : $getUrl")
        val request: Request = Request.Builder()
            .url(getUrl)
            .get()
            .build()

        client.newCall(request).enqueue(responseCallback(callback))
    }

    fun post(url: String, name: String, mail: String , callback: (String?, String?) -> Unit) {
        Log.d("ApiClient", "POST ${Constants.USERS_API}")
        val postUrl = "$url${Constants.USERS_API}"
        val jsonBody = JSONObject().apply {
            put("name", name)
            put("email", mail)
        }.toString()

        val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(postUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(responseCallback(callback))
    }

    fun put(url: String, id:String, name: String, mail: String , callback: (String?, String?) -> Unit) {
        Log.d("ApiClient", "PUT ${Constants.USERS_API}$id")
        val putUrl = "$url${Constants.USERS_API}$id"
        val jsonBody = JSONObject().apply {
            put("name", name)
            put("email", mail)
        }.toString()

        val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(putUrl)
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(responseCallback(callback))
    }

    fun delete(url: String, id:String, callback: (String?, String?) -> Unit) {
        Log.d("ApiClient", "DELETE ${Constants.USERS_API}$id")
        val deleteUrl = "$url${Constants.USERS_API}$id"
        val request = Request.Builder()
            .url(deleteUrl)
            .delete()
            .build()

        client.newCall(request).enqueue(responseCallback(callback))
    }

    private fun responseCallback(callback: (String?, String?) -> Unit): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                callback(res, null)
            }
        }
    }
}