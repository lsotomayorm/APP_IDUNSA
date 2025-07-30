package com.idunsa.idunsa_app.inicio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<TextInputEditText>(R.id.usernameInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registroButton = findViewById<Button>(R.id.RegistroButton)

        loginButton.setOnClickListener {
            val cui = usernameInput.text?.toString()?.trim()?.toIntOrNull()
            val password = passwordInput.text?.toString()?.trim()

            if (cui == null || password.isNullOrEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val loginRequest = LoginRequest(cui, password)

                RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            val usuario = response.body()

                            if (usuario != null) {
                                val sharedPref = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
                                with(sharedPref.edit()) {
                                    putInt("usuario_id", usuario.cui)
                                    putString("usuario_rol", usuario.rol)
                                    apply()
                                }

                                Toast.makeText(this@LoginActivity, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error de conexion: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        registroButton.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}
