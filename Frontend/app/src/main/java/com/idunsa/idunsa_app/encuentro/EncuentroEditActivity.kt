package com.idunsa.idunsa_app.encuentro

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.EncuentroUpdateRequest
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class EncuentroEditActivity : AppCompatActivity() {

    private var encuentroId: Long = -1
    private var nombreEquipo1: String? = null
    private var nombreEquipo2: String? = null
    private lateinit var scoreEquipo1: EditText
    private lateinit var scoreEquipo2: EditText
    private lateinit var ganadorSpinner: Spinner
    private lateinit var estadoSpinner: Spinner
    private lateinit var btnGuardar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encuentro_edit)

        scoreEquipo1 = findViewById(R.id.scoreEquipo1)
        scoreEquipo2 = findViewById(R.id.scoreEquipo2)
        ganadorSpinner = findViewById(R.id.ganadorSpinner)
        btnGuardar = findViewById(R.id.btnGuardar)
        estadoSpinner = findViewById(R.id.estadoSpinner)

        val opcionesEstado = arrayOf("PENDIENTE", "EN CURSO", "FINALIZADO")
        val estadoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesEstado)
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estadoSpinner.adapter = estadoAdapter


        ganadorSpinner.isEnabled = false

        estadoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val estadoSeleccionado = parent.getItemAtPosition(position).toString()
                ganadorSpinner.isEnabled = estadoSeleccionado == "FINALIZADO"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        encuentroId = intent.getLongExtra("encuentro_id", -1)
        nombreEquipo1 = intent.getStringExtra("equipo1_nombre")
        nombreEquipo2 = intent.getStringExtra("equipo2_nombre")

        if (nombreEquipo1 == null || nombreEquipo2 == null) {
            Toast.makeText(this, "Nombres de equipos no recibidos", Toast.LENGTH_SHORT).show()
        }


        val opcionesGanador = arrayOf(nombreEquipo1, nombreEquipo2)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesGanador)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ganadorSpinner.adapter = spinnerAdapter


        if (encuentroId != -1L) {
            cargarEncuentro(encuentroId)
        } else {
            Toast.makeText(this, "ID de encuentro inválido", Toast.LENGTH_SHORT).show()
        }

        btnGuardar.setOnClickListener {
            val score1 = scoreEquipo1.text.toString().toFloatOrNull()
            val score2 = scoreEquipo2.text.toString().toFloatOrNull()
            val ganador = if (ganadorSpinner.isEnabled) ganadorSpinner.selectedItem.toString() else null

            if (score1 != null && score2 != null) {
                actualizarEncuentro(encuentroId, score1, score2, ganador)
            } else {
                Toast.makeText(this, "Por favor ingrese los puntajes válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun cargarEncuentro(encuentroId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerEncuentroPorId(encuentroId)
                if (response.isSuccessful) {
                    val encuentro = response.body()
                    if (encuentro != null) {
                        scoreEquipo1.setText((encuentro.score1 ?: 0f).toInt().toString())
                        scoreEquipo2.setText((encuentro.score2 ?: 0f).toInt().toString())

                        val ganador = when (encuentro.ganador) {
                            nombreEquipo1 -> 0
                            nombreEquipo2 -> 1
                            else -> 0
                        }

                        ganadorSpinner.setSelection(ganador)

                        val estadoIndex = when (encuentro.estado) {
                            "PENDIENTE" -> 0
                            "EN_CURSO" -> 1
                            "FINALIZADO" -> 2
                            else -> 0
                        }
                        estadoSpinner.setSelection(estadoIndex)

                    } else {
                        Toast.makeText(this@EncuentroEditActivity, "Encuentro no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EncuentroEditActivity, "Error al cargar el encuentro", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EncuentroEditActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarEncuentro(encuentroId: Long, score1: Float?, score2: Float?, ganador: String?) {
        lifecycleScope.launch {
            try {
                val estadoSeleccionado = estadoSpinner.selectedItem.toString().replace(" ", "_")

                val encuentroUpdateRequest = EncuentroUpdateRequest(
                    score1 = score1,
                    score2 = score2,
                    ganador = ganador,
                    estado = estadoSeleccionado
                )

                val response = RetrofitClient.instance.actualizarEncuentro(encuentroId, encuentroUpdateRequest)

                if (response.isSuccessful) {
                    Toast.makeText(this@EncuentroEditActivity, "Encuentro actualizado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EncuentroEditActivity, "Error al actualizar el encuentro", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EncuentroEditActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
