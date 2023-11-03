/**
 * MainActivity.kt
 * android-api-sample
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */

package com.kazu.android_api_sample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.kazu.android_api_sample.R
import com.kazu.android_api_sample.client.ApiClient
import com.kazu.android_api_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /** binding */
    private lateinit var binding: ActivityMainBinding

    /** radio value */
    private var radioValue: String = "";

    /** ApiClient instance */
    private val apiClient = ApiClient()

    /** TAG */
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setButton()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setButton() {
        binding.defaultButton.setOnClickListener {
            defaultEditText()
        }

        binding.apiButton.setOnClickListener {
            val userId: String = binding.editId.text.toString().takeIf { it.isNotBlank() } ?: ""
            val name: String = binding.editName.text.toString().takeIf { it.isNotBlank() } ?: ""
            val mail: String = binding.editMail.text.toString().takeIf { it.isNotBlank() } ?: ""
            // Commented out for unused.
//            var message: String = binding.editMessage.text.toString().takeIf { it.isNotBlank() } ?: ""
            val url: String = binding.editUrl.text.toString().takeIf { it.isNotBlank() } ?: ""

            if (url.isNotEmpty()) {
                when (radioValue) {
                    "GET" -> getUserData(url, userId)
                    "GET ALL" -> getAllUserData(url)
                    "POST" -> registerUserData(url, name, mail)
                    "PUT" -> updateUserData(url, userId, name, mail)
                    "DELETE" -> deleteUserData(url, userId)
                    else -> unselectRadioButton()
                }
            } else {
                Toast.makeText(this, getString(R.string.not_url_text), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "URLが未入力です。")
            }
        }

        binding.apiRadio.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedValue = radioButton.text.toString()
            radioValue = selectedValue
            Log.d(TAG, radioValue)
        }
    }

    private fun defaultEditText() {
        binding.editId.setText(getString(R.string.default_id_text))
        binding.editName.setText(getString(R.string.default_name_text))
        binding.editMail.setText(getString(R.string.default_mail_text))
        binding.editMessage.setText(getString(R.string.default_message_text))
        binding.editUrl.setText(getString(R.string.default_url_text))
    }

    private fun getUserData(url: String, userId: String) {
        apiClient.get(url, userId) { responseData, errorMessage ->
            handleResponse(responseData, errorMessage)
        }
    }

    private fun getAllUserData(url: String) {
        apiClient.getAllUser(url) { responseData, errorMessage ->
            handleResponse(responseData, errorMessage)
        }
    }

    private fun registerUserData(url: String, name: String, mail: String) {
        apiClient.post(url, name, mail) { responseData, errorMessage ->
            handleResponse(responseData, errorMessage)
        }
    }

    private fun updateUserData(url: String, userId: String, name: String, mail: String) {
        if (name != "" && mail != "") {
            apiClient.put(url, userId, name, mail) { responseData, errorMessage ->
                handleResponse(responseData, errorMessage)
            }
        } else {
            Toast.makeText(this, getString(R.string.id_mail_not_entered), Toast.LENGTH_SHORT).show()
            Log.d(TAG, "ユーザ名またはメールアドレスが未入力です。")
        }
    }

    private fun deleteUserData(url: String, userId: String) {
        if (userId != "") {
            apiClient.delete(url, userId) { responseData, errorMessage ->
                handleResponse(responseData, errorMessage)
            }
        } else {
            Toast.makeText(this, getString(R.string.id_mail_not_entered), Toast.LENGTH_SHORT).show()
            Log.d(TAG, "ユーザIDまたはメールアドレスが未入力です。")
        }
    }

    private fun handleResponse(response: String?, errorMessage: String?) {
        runOnUiThread {
            if (response != null) {
                Toast.makeText(this, "Response: $response", Toast.LENGTH_LONG).show()
            } else if (errorMessage != null) {
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun unselectRadioButton() {
        Toast.makeText(this, getString(R.string.select_http_status), Toast.LENGTH_SHORT).show()
        Log.d("TAG", "送信するHTTPステータスを選択してください。")
    }
}