package com.idunsa.idunsa_app.torneo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.RetrofitClient
import com.idunsa.idunsa_app.network.TorneoRequest
import java.util.Calendar
import kotlinx.coroutines.launch
import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.idunsa.idunsa_app.network.Deporte


class TorneoEditActivity : AppCompatActivity() {

    private lateinit var nombreInput: EditText
    private lateinit var tipoDeporteInput: EditText
    private lateinit var direccionInput: EditText
    private lateinit var fechaInput: EditText
    private lateinit var horaInput: EditText
    private lateinit var guardarButton: Button
    private lateinit var atrasButton: Button
    private lateinit var deporteSpinner: Spinner
    private var deporteSeleccionado: Deporte? = null

    private var torneoId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_torneo_edit)

        nombreInput = findViewById(R.id.torneoNameInput)
        direccionInput = findViewById(R.id.direccionInput)
        fechaInput = findViewById(R.id.fechaInput)
        horaInput = findViewById(R.id.horaInput)
        guardarButton = findViewById(R.id.saveButton)
        atrasButton = findViewById(R.id.backButton)
        deporteSpinner = findViewById(R.id.deporteSpinner)

        torneoId = intent.getLongExtra("torneo_id", -1)
        val nombre = intent.getStringExtra("torneo_nombre") ?: ""
        val direccion = intent.getStringExtra("torneo_direccion") ?: ""
        val fecha = intent.getStringExtra("torneo_fecha") ?: ""
        val hora = intent.getStringExtra("torneo_hora") ?: ""
        val nombreDeporte = intent.getStringExtra("torneo_deporte") ?: ""

        // Rellenar campos de texto
        nombreInput.setText(nombre)
        direccionInput.setText(direccion)
        fechaInput.setText(fecha)
        horaInput.setText(hora)

        fechaInput.setOnClickListener { mostrarDatePicker(fechaInput) }
        horaInput.setOnClickListener { mostrarTimePicker(horaInput) }

        // Cargar deportes
        cargarDeportes(nombreDeporte)

        guardarButton.setOnClickListener {
            val nombre = nombreInput.text.toString().trim()
            val direccion = direccionInput.text.toString().trim()
            val fecha = fechaInput.text.toString().trim()
            val hora = horaInput.text.toString().trim()

            if (nombre.isEmpty() || direccion.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val deporteId = deporteSeleccionado?.id?: run {
                Toast.makeText(this, "Selecciona un deporte", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoTorneo = TorneoRequest(
                nombre = nombre,
                direccion = direccion,
                fecha = fecha,
                hora = hora,
                eventoId = -1L, // si ya no lo usas, ignóralo en backend
                deporteId = deporteId
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.actualizarTorneo(torneoId, nuevoTorneo)
                    if (response.isSuccessful) {
                        Toast.makeText(this@TorneoEditActivity, "Torneo actualizado", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this@TorneoEditActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@TorneoEditActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        atrasButton.setOnClickListener {
            finish()
        }
    }

    private fun cargarDeportes(nombreDeporte: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerDeportes()
                if (response.isSuccessful) {
                    val deportes = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@TorneoEditActivity, android.R.layout.simple_spinner_item, deportes.map { it.nombre })
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    deporteSpinner.adapter = adapter

                    val index = deportes.indexOfFirst { it.nombre.equals(nombreDeporte, ignoreCase = true) }
                    if (index != -1) {
                        deporteSpinner.setSelection(index)
                        deporteSeleccionado = deportes[index]
                    }

                    deporteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            deporteSeleccionado = deportes[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            deporteSeleccionado = null
                        }
                    }
                } else {
                    Toast.makeText(this@TorneoEditActivity, "No se pudieron cargar los deportes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@TorneoEditActivity, "Error al obtener deportes: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun mostrarDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, y, m, d ->
                val fecha = String.format("%04d-%02d-%02d", y, m + 1, d)
                editText.setText(fecha)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun mostrarTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, h, m ->
                val horaFormateada = String.format("%02d:%02d", h, m)
                editText.setText(horaFormateada)
            },
            hour, minute, true
        )

        timePickerDialog.show()
    }
}

