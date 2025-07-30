package com.idunsa.idunsa_app.inicio

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {

    private lateinit var cuiInput: TextInputEditText
    private lateinit var nombreInput: TextInputEditText
    private lateinit var apellidoInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var repeatPasswordInput: TextInputEditText
    private lateinit var escuelaSpinner: Spinner
    private lateinit var registerButton: Button
    private lateinit var salirButton: Button


    private var escuelas: List<Escuela> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        cuiInput = findViewById(R.id.cuiInput)
        nombreInput = findViewById(R.id.nombreInput)
        apellidoInput = findViewById(R.id.apellidoInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        repeatPasswordInput = findViewById(R.id.repeatPasswordInput)
        escuelaSpinner = findViewById(R.id.escuelaSpinner)
        registerButton = findViewById(R.id.registerButton)
        salirButton = findViewById(R.id.salirButton)

        cargarEscuelas()

        salirButton.setOnClickListener {
            finish()
        }
        registerButton.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun cargarEscuelas() {
        RetrofitClient.instance.obtenerEscuelas().enqueue(object : Callback<List<Escuela>> {
            override fun onResponse(call: Call<List<Escuela>>, response: Response<List<Escuela>>) {
                if (response.isSuccessful) {
                    escuelas = response.body() ?: listOf()
                    val nombres = escuelas.map { it.nombre }
                    val adapter = ArrayAdapter(this@RegistroActivity, android.R.layout.simple_spinner_item, nombres)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    escuelaSpinner.adapter = adapter
                } else {
                    Toast.makeText(this@RegistroActivity, "Error al cargar escuelas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Escuela>>, t: Throwable) {
                Toast.makeText(this@RegistroActivity, "Fallo de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registrarUsuario() {
        val cui = cuiInput.text?.toString()?.toIntOrNull()
        val nombre = nombreInput.text?.toString()?.trim()
        val apellido = apellidoInput.text?.toString()?.trim()
        val email = emailInput.text?.toString()?.trim()
        val password = passwordInput.text?.toString()?.trim()
        val repeatPassword = repeatPasswordInput.text?.toString()?.trim()
        val escuelaSeleccionada = escuelaSpinner.selectedItem?.toString()

        if (cui == null || nombre.isNullOrEmpty() || apellido.isNullOrEmpty() || email.isNullOrEmpty()
            || password.isNullOrEmpty() || repeatPassword.isNullOrEmpty() || escuelaSeleccionada.isNullOrEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != repeatPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val request = RegistroRequest(cui, nombre, apellido, email, password, escuelaSeleccionada)

        RetrofitClient.instance.registrar(request).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegistroActivity, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@RegistroActivity, "Fallo de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
