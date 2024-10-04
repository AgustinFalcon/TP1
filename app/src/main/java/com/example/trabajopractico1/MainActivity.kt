package com.example.trabajopractico1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabajopractico1.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getSharedPreferences(RegisterActivity.CREDENTIALS, MODE_PRIVATE)
        val edit = preferences.edit()


        /*binding.etUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })*/

        val autoLogin = preferences.getBoolean("autoLogin", false)
        if (autoLogin == true) {
            goToHomeActivity()
        }

        binding.btnSingIn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            val checkBox = binding.checkbox.isChecked
            val user = binding.etUser.text.toString()
            val password = binding.etPass.text.toString()

            edit.putBoolean("autoLogin", checkBox)
            edit.apply()

            if (validateAutoLogin()) {
                goToHomeActivity()

            } else {
                if (validateData(user, password) == true) {
                    edit.putBoolean("autoLogin", checkBox)
                    edit.apply()
                    goToHomeActivity()
                } else {
                    Toast.makeText(this, "Usuario incorrecto!", Toast.LENGTH_SHORT).show()
                    edit.putBoolean("autoLogin", false)
                    edit.apply()

                }
            }
        }

        binding.btnSingUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateAutoLogin(): Boolean {
        val autoLogin = preferences.getBoolean("autoLogin", false)
        return autoLogin // false o true
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val autoLogin = preferences.getBoolean("autoLogin", false)
        if (autoLogin == true) {
            goToHomeActivity()
        }
    }


    private fun validateData(user: String, password: String): Boolean {
        var person = Persona("","", null)
        try {

            val personJson = preferences.getString("person", "")
            val gson = Gson()

            person = gson.fromJson(personJson, Persona::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        return if (user == person.userName && password == person.password) {
             true
        } else {
             false
        }
    }


}