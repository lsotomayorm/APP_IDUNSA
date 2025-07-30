package com.idunsa.idunsa_app.evento

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.*
import java.util.Calendar

class EventAdd : AppCompatActivity() {

    private lateinit var nombreEventoInput: EditText
    private lateinit var fechaInicioInput: EditText
    private lateinit var fechaFinInput: EditText
    private lateinit var guardarButton: Button
    private lateinit var atrasButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_add)

        nombreEventoInput = findViewById(R.id.eventNameInput)
        fechaInicioInput = findViewById(R.id.startDateInput)
        fechaFinInput = findViewById(R.id.endDateInput)
        guardarButton = findViewById(R.id.saveButton)
        atrasButton = findViewById(R.id.backButton)

        fechaInicioInput.setOnClickListener {
            mostrarDatePicker(fechaInicioInput)
        }

        fechaFinInput.setOnClickListener {
            mostrarDatePicker(fechaFinInput)
        }

        guardarButton.setOnClickListener {
            val nombre = nombreEventoInput.text.toString().trim()
            val fechaInicio = fechaInicioInput.text.toString().trim()
            val fechaFin = fechaFinInput.text.toString().trim()

            if (nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoEvento = EventoRequest(nombre, fechaInicio, fechaFin)

            RetrofitClient.instance.crearEvento(nuevoEvento).enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EventAdd, "Evento guardado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EventAdd, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                    Toast.makeText(this@EventAdd, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        atrasButton.setOnClickListener {
            finish()
        }
    }

    private fun mostrarDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val anio = calendar.get(Calendar.YEAR)
        val mes = calendar.get(Calendar.MONTH)
        val dia = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                editText.setText(fechaSeleccionada)
            },
            anio, mes, dia
        )

        datePickerDialog.show()
    }
}
