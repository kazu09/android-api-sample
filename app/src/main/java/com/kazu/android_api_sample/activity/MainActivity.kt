package com.kazu.android_api_sample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.kazu.android_api_sample.client.ApiClient
import com.kazu.android_api_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /** binding */
    private lateinit var binding: ActivityMainBinding

    private var radioValue: String = "";

    private val apiClient = ApiClient()

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
            var message: String = binding.editMessage.text.toString().takeIf { it.isNotBlank() } ?: ""
            val url: String = binding.editUrl.text.toString().takeIf { it.isNotBlank() } ?: ""

            if (url.isNotEmpty()) {
                when (radioValue) {
                    "GET" -> getUserData(url, userId)
                    "GET ALL" -> getAllUserData(url)
                    "POST" -> registerUserData(url, name, mail)
                    "PUT" -> updateUserData(url, userId, name, mail)
                    "DELETE" -> deleteUserData(url, userId)
                    else -> Log.d("MainActivity", "送信するステータスを選択してください")
                }
            } else {
                Log.d("MainActivity", "URLが未入力です。")
            }
        }

        binding.apiRadio.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedValue = radioButton.text.toString()
            radioValue = selectedValue
            Log.d("MainActivity",radioValue)
        }
    }

    private fun defaultEditText() {
        binding.editId.setText("0")
        binding.editName.setText("Sample Name")
        binding.editMail.setText("test@sample.com")
        binding.editMessage.setText("Hello Android!!")
        binding.editUrl.setText("http://127.0.0.1:3000")
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
            Log.d("MainActivity", "ユーザ名またはメールアドレスが未入力です。")
        }
    }

    private fun deleteUserData(url: String, userId: String) {
        if (userId != "") {
            apiClient.delete(url, userId) { responseData, errorMessage ->
                handleResponse(responseData, errorMessage)
            }
        } else {
            Log.d("MainActivity", "ユーザ名またはメールアドレスが未入力です。")
        }
    }

    private fun handleResponse(response: String?, errorMessage: String?) {
        runOnUiThread {
            if (response != null) {
                Toast.makeText(applicationContext, "Response: $response", Toast.LENGTH_LONG).show()
            } else if (errorMessage != null) {
                Toast.makeText(applicationContext, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            }
        }
    }
}