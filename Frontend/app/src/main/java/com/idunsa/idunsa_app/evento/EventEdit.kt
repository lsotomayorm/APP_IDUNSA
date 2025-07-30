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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventEdit : AppCompatActivity() {
    private lateinit var eventNameInput: EditText
    private lateinit var startDateInput: EditText
    private lateinit var endDateInput: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: Button

    private var eventoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_edit)

        eventNameInput = findViewById(R.id.eventNameInput)
        startDateInput = findViewById(R.id.startDateInput)
        endDateInput = findViewById(R.id.endDateInput)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        eventoId = intent.getLongExtra("evento_id", -1)
        val nombre = intent.getStringExtra("evento_nombre") ?: ""
        val fechaInicio = intent.getStringExtra("evento_fechaInicio") ?: ""
        val fechaFin = intent.getStringExtra("evento_fechaFin") ?: ""

        eventNameInput.setText(nombre)
        startDateInput.setText(fechaInicio)
        endDateInput.setText(fechaFin)

        backButton.setOnClickListener { finish() }

        saveButton.setOnClickListener {
            val updatedNombre = eventNameInput.text.toString()
            val updatedInicio = startDateInput.text.toString()
            val updatedFin = endDateInput.text.toString()

            val eventoRequest = EventoRequest(updatedNombre, updatedInicio, updatedFin)

            RetrofitClient.instance.actualizarEvento(eventoId, eventoRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EventEdit, "Evento actualizado", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EventEdit, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@EventEdit, "Error de red", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
