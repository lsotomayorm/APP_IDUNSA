package com.idunsa.idunsa_app.torneo

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.RetrofitClient
import com.idunsa.idunsa_app.network.TorneoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.network.Deporte
import kotlinx.coroutines.launch


class TorneoAddActivity : AppCompatActivity() {

    private lateinit var nombreInput: EditText
    private lateinit var direccionInput: EditText
    private lateinit var fechaInput: EditText
    private lateinit var horaInput: EditText
    private lateinit var guardarButton: Button
    private lateinit var atrasButton: Button
    private lateinit var deporteSpinner: Spinner
    private var deporteSeleccionado: Deporte? = null


    private var eventoId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_torneo_add)

        nombreInput = findViewById(R.id.torneoNameInput)
        direccionInput = findViewById(R.id.direccionInput)
        fechaInput = findViewById(R.id.fechaInput)
        horaInput = findViewById(R.id.horaInput)
        guardarButton = findViewById(R.id.saveButton)
        atrasButton = findViewById(R.id.backButton)
        deporteSpinner = findViewById(R.id.deporteSpinner)
        cargarDeportes()




        eventoId = intent.getLongExtra("evento_id", -1)
        if (eventoId == -1L) {
            Toast.makeText(this, "ID del evento no recibido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fechaInput.setOnClickListener {
            mostrarDatePicker(fechaInput)
        }

        horaInput.setOnClickListener {
            mostrarTimePicker(horaInput)
        }

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
                eventoId = eventoId,
                deporteId = deporteId
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.crearTorneo(nuevoTorneo)
                    if (response.isSuccessful && response.body() != null) {
                        val torneoCreado = response.body()!!
                        Toast.makeText(this@TorneoAddActivity, "Torneo creado: ${torneoCreado.nombre}", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        val error = response.errorBody()?.string() ?: "Respuesta no exitosa"
                        Toast.makeText(this@TorneoAddActivity, "Error al crear torneo: $error", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@TorneoAddActivity, "Error de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }


        atrasButton.setOnClickListener {
            finish()
        }
    }

    private fun cargarDeportes() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerDeportes()
                if (response.isSuccessful) {
                    val deportes = response.body() ?: emptyList()
                    if (deportes.isNotEmpty()) {
                        val adapter = android.widget.ArrayAdapter(
                            this@TorneoAddActivity,
                            android.R.layout.simple_spinner_item,
                            deportes.map { it.nombre }
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        deporteSpinner.adapter = adapter

                        // Guarda la selección actual
                        deporteSpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                                deporteSeleccionado = deportes[position]
                            }

                            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                                deporteSeleccionado = null
                            }
                        })
                    } else {
                        Toast.makeText(this@TorneoAddActivity, "No hay deportes disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@TorneoAddActivity, "Error al cargar deportes: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@TorneoAddActivity, "Error al cargar deportes: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
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
