package com.idunsa.idunsa_app.reglamento

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class ReglamentoHomeActivity : AppCompatActivity() {

    private lateinit var tvTituloReglamento: TextView
    private lateinit var etReglamentoTexto: EditText
    private lateinit var btnAgregar: Button
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnAtras: Button
    private var esAdmin: Boolean = false

    private var torneoId: Long = -1L
    private var tipoDeporte: String = ""
    private var reglamentoActual: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reglamento_home)

        tvTituloReglamento = findViewById(R.id.tvTituloReglamento)
        etReglamentoTexto = findViewById(R.id.etReglamentoTexto)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnAtras = findViewById(R.id.btnAtras)

        torneoId = intent.getLongExtra("torneo_id", -1)
        tipoDeporte = intent.getStringExtra("torneo_deporte") ?: "Deporte"
        tvTituloReglamento.text = "Reglamento - $tipoDeporte"

        val sharedPref = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
        val rolUsuario = sharedPref.getString("usuario_rol", "")
        esAdmin = rolUsuario == "ADMIN"

        if (!esAdmin) {
            btnAgregar.visibility = View.GONE
            btnEditar.visibility = View.GONE
            btnEliminar.visibility = View.GONE
            etReglamentoTexto.isEnabled = false
        }


        obtenerReglamento(torneoId)

        btnAtras.setOnClickListener { finish() }

        btnAgregar.setOnClickListener {
            if (btnAgregar.text == "Guardar") {
                guardarReglamento(etReglamentoTexto.text.toString())
            } else {
                activarModoEdicion()
                btnAgregar.text = "Guardar"
                btnAgregar.setBackgroundColor(getColor(R.color.verde))
            }
        }

        btnEditar.setOnClickListener {
            if (btnEditar.text == "Guardar") {
                guardarReglamento(etReglamentoTexto.text.toString())
            } else {
                activarModoEdicion()
                btnEditar.text = "Guardar"
            }
        }

        btnEliminar.setOnClickListener {
            mostrarDialogoEliminacion()
        }
    }

    private fun obtenerReglamento(torneoId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerTorneoPorId(torneoId)
                if (response.isSuccessful) {
                    val torneo = response.body()
                    reglamentoActual = torneo?.reglamento

                    if (reglamentoActual?.trim()?.isEmpty() != false) {
                        mostrarSoloAgregar()
                    } else {
                        mostrarReglamentoExistente(reglamentoActual!!)
                    }
                } else {
                    Toast.makeText(this@ReglamentoHomeActivity, "Error al obtener el reglamento", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ReglamentoHomeActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarSoloAgregar() {
        etReglamentoTexto.setText("")
        etReglamentoTexto.isEnabled = false

        btnAgregar.visibility = View.VISIBLE
        btnEditar.visibility = View.GONE
        btnEliminar.visibility = View.GONE

        if (!esAdmin) {
            btnAgregar.visibility = View.GONE
            btnEditar.visibility = View.GONE
            btnEliminar.visibility = View.GONE
            etReglamentoTexto.isEnabled = false
        }
    }

    private fun mostrarReglamentoExistente(texto: String) {
        etReglamentoTexto.setText(texto)
        etReglamentoTexto.isEnabled = false

        if (esAdmin) {
            btnAgregar.visibility = View.GONE
            btnEditar.visibility = View.VISIBLE
            btnEliminar.visibility = View.VISIBLE
        } else {
            btnAgregar.visibility = View.GONE
            btnEditar.visibility = View.GONE
            btnEliminar.visibility = View.GONE
        }
    }


    private fun activarModoEdicion() {
        etReglamentoTexto.isEnabled = true
        etReglamentoTexto.requestFocus()
    }

    private fun desactivarModoEdicion() {
        etReglamentoTexto.isEnabled = false
        btnAgregar.text = "Agregar"
        btnEditar.text = "Editar"
    }

    private fun guardarReglamento(nuevoTexto: String) {
        lifecycleScope.launch {
            try {
                val body = mapOf("reglamento" to nuevoTexto)
                val response = RetrofitClient.instance.actualizarReglamento(torneoId, body)

                if (response.isSuccessful) {
                    Toast.makeText(this@ReglamentoHomeActivity, "Reglamento guardado", Toast.LENGTH_SHORT).show()
                    reglamentoActual = nuevoTexto
                    desactivarModoEdicion()
                    mostrarReglamentoExistente(nuevoTexto)
                } else {
                    Toast.makeText(this@ReglamentoHomeActivity, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ReglamentoHomeActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDialogoEliminacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de eliminar el reglamento?")
            .setPositiveButton("Sí") { _, _ -> eliminarReglamento() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun eliminarReglamento() {
        lifecycleScope.launch {
            try {
                val body = mapOf<String, String?>("reglamento" to null)
                val response = RetrofitClient.instance.actualizarReglamento(torneoId, body)

                if (response.isSuccessful) {
                    Toast.makeText(this@ReglamentoHomeActivity, "Reglamento eliminado", Toast.LENGTH_SHORT).show()
                    mostrarSoloAgregar()
                } else {
                    Toast.makeText(this@ReglamentoHomeActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ReglamentoHomeActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
