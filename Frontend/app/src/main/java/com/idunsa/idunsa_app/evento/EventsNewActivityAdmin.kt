package com.idunsa.idunsa_app.evento

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.Evento
import com.idunsa.idunsa_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsNewActivityAdmin : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addEventButton: Button
    private lateinit var backButton: Button
    private lateinit var textoVacio: TextView
    private var esAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_new_admin)

        recyclerView = findViewById(R.id.eventsRecyclerView)
        textoVacio = findViewById(R.id.textoVacio)
        addEventButton = findViewById(R.id.addEventButton)
        backButton = findViewById(R.id.backButton)

        val sharedPref = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
        esAdmin = sharedPref.getString("usuario_rol", "") == "ADMIN"

        if (!esAdmin) {
            addEventButton.visibility = View.GONE
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        backButton.setOnClickListener { finish() }

        addEventButton.setOnClickListener {
            startActivity(Intent(this, EventAdd::class.java))
        }

        obtenerEventos()
    }

    override fun onResume() {
        super.onResume()
        obtenerEventos()
    }

    private fun obtenerEventos() {
        RetrofitClient.instance.obtenerEventos().enqueue(object : Callback<List<Evento>> {
            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
                if (response.isSuccessful) {
                    val eventos = response.body() ?: emptyList()

                    if (eventos.isEmpty()) {
                        textoVacio.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        textoVacio.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        recyclerView.adapter = EventsAdapter(
                            eventos = eventos,
                            esAdmin = esAdmin,
                            onEditarClick = { evento ->
                                val intent = Intent(this@EventsNewActivityAdmin, EventEdit::class.java)
                                intent.putExtra("evento_id", evento.id)
                                intent.putExtra("evento_nombre", evento.nombre)
                                intent.putExtra("evento_fechaInicio", evento.fechaInicio)
                                intent.putExtra("evento_fechaFin", evento.fechaFin)
                                startActivity(intent)
                            },
                            onItemClick = { evento ->
                                val intent = Intent(this@EventsNewActivityAdmin, EventHomeActivity::class.java)
                                intent.putExtra("evento_id", evento.id)
                                intent.putExtra("evento_nombre", evento.nombre)
                                intent.putExtra("evento_fechaInicio", evento.fechaInicio)
                                intent.putExtra("evento_fechaFin", evento.fechaFin)
                                startActivity(intent)
                            },
                            onEliminarClick = { evento ->
                                AlertDialog.Builder(this@EventsNewActivityAdmin)
                                    .setTitle("Confirmar eliminación")
                                    .setMessage("¿Estás seguro de eliminar el evento: ${evento.nombre}?")
                                    .setPositiveButton("Eliminar") { _, _ ->
                                        eliminarEvento(evento.id)
                                    }
                                    .setNegativeButton("Cancelar", null)
                                    .show()
                            }
                        )
                    }
                } else {
                    Toast.makeText(this@EventsNewActivityAdmin, "Error al obtener eventos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
                Toast.makeText(this@EventsNewActivityAdmin, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarEvento(id: Long) {
        RetrofitClient.instance.eliminarEvento(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EventsNewActivityAdmin, "Evento eliminado", Toast.LENGTH_SHORT).show()
                    obtenerEventos()
                } else {
                    Toast.makeText(this@EventsNewActivityAdmin, "Error al eliminar evento", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EventsNewActivityAdmin, "Error de red al eliminar", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
