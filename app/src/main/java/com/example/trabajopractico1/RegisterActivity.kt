package com.example.trabajopractico1

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabajopractico1.databinding.ActivityRegisterBinding
import com.google.gson.Gson


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    val arrayColors: Array<Colors> = Colors.values()
    var colorSelected: Colors? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, arrayColors)
        binding.spinnerColor.adapter = adapter
        //binding.spinnerColor.setOnItemSelectedListener(this)

        binding.spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                colorSelected = arrayColors.get(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                colorSelected = null
            }
        }



        binding.btnSingIn.setOnClickListener {
            val user = binding.etUser.text.toString()
            val password = binding.etPass.text.toString()

            if (user.isNotBlank() && password.isNotBlank() && colorSelected != null) {
                val preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
                val edit = preferences.edit()

                val persona = Persona(userName = user, password = password, color = colorSelected)
                val gson = Gson()

                //edit.putString("user", user)
                //edit.putString("password", password)

                val personInJsonFormat = gson.toJson(persona)
                edit.putString("person", personInJsonFormat)

                edit.apply()
                goToMainActivity()

            } else {
                Toast.makeText(this, "Debe completar todos los campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val CREDENTIALS = "Credenciales"
    }

}