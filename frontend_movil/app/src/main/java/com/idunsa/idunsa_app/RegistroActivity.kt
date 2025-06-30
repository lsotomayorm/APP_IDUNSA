package com.idunsa.idunsa_app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val cuiInput = findViewById<TextInputEditText>(R.id.cuiInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val repeatPasswordInput = findViewById<TextInputEditText>(R.id.repeatPasswordInput)
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val carreraSpinner = findViewById<Spinner>(R.id.carreraSpinner)
        val registerButton = findViewById<Button>(R.id.registerButton)

        val carreras = listOf("Ciencias de la Computación", "Arquitectura", "Ing. Sistemas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carreras)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        carreraSpinner.adapter = adapter

        registerButton.setOnClickListener {
            val cui = cuiInput.text?.toString()?.trim()
            val password = passwordInput.text?.toString()
            val repeatPassword = repeatPasswordInput.text?.toString()
            val email = emailInput.text?.toString()?.trim()
            val carrera = carreraSpinner.selectedItem as String

            // Validaciones
            if (cui.isNullOrEmpty() || !cui.matches(Regex("\\d+"))) {
                showToast("Ingrese un CUI válido (solo números)")
                return@setOnClickListener
            }
            if (password.isNullOrEmpty()) {
                showToast("Ingrese una contraseña")
                return@setOnClickListener
            }
            if (password != repeatPassword) {
                showToast("Las contraseñas no coinciden")
                return@setOnClickListener
            }
            if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Ingrese un correo válido")
                return@setOnClickListener
            }
            if (!email.endsWith("@unsa.edu.pe")) {
                showToast("El correo debe ser @unsa.edu.pe")
                return@setOnClickListener
            }



            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
