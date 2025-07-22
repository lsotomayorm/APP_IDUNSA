package com.idunsa.idunsa_app.evento


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.RetrofitClient
import com.idunsa.idunsa_app.network.Torneo
import com.idunsa.idunsa_app.torneo.TorneoAdapter
import com.idunsa.idunsa_app.torneo.TorneoAddActivity
import com.idunsa.idunsa_app.torneo.TorneoEditActivity
import com.idunsa.idunsa_app.torneo.TorneoHomeActivity
import kotlinx.coroutines.launch

class EventHomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textoVacio: TextView
    private lateinit var addTorneoButton: Button
    private lateinit var backButton: Button
    private lateinit var searchBar: EditText
    private var eventoId: Long = -1L
    private lateinit var nombreEvento: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_home)

        eventoId = intent.getLongExtra("evento_id", -1)
        nombreEvento = intent.getStringExtra("evento_nombre") ?: "Evento"
        var fechaInicio = intent.getStringExtra("evento_fechaInicio") ?: "-"
        var fechaFin = intent.getStringExtra("evento_fechaFin") ?: "-"
        title = nombreEvento

        recyclerView = findViewById(R.id.tournamentsRecyclerView)
        textoVacio = findViewById(R.id.textoVacio)
        addTorneoButton = findViewById(R.id.addTournamentButton)
        backButton = findViewById(R.id.backButton)
        searchBar = findViewById(R.id.searchBar)

        val eventTitleText = findViewById<TextView>(R.id.eventTitleText)
        val eventDatesText = findViewById<TextView>(R.id.eventDatesText)
        eventTitleText.text = nombreEvento
        eventDatesText.text = "Inicio: $fechaInicio | Fin: $fechaFin"

        recyclerView.layoutManager = LinearLayoutManager(this)

        addTorneoButton.setOnClickListener {
            val intent = Intent(this@EventHomeActivity, TorneoAddActivity::class.java)
            intent.putExtra("evento_id", eventoId)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        obtenerTorneosPorEvento()
    }

    private fun obtenerTorneosPorEvento() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerTorneosPorEvento(eventoId)

                if (response.isSuccessful) {
                    val torneoResponseList = response.body() ?: emptyList()

                    val torneos = torneoResponseList.map {
                        Torneo(
                            id = it.id,
                            nombre = it.nombre,
                            deporte = it.deporte,
                            direccion = it.direccion,
                            fecha = it.fecha,
                            hora = it.hora,
                            eventoId = it.eventoId,
                            reglamento = it.reglamento
                        )
                    }

                    if (torneos.isEmpty()) {
                        textoVacio.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        textoVacio.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE

                        recyclerView.adapter = TorneoAdapter(
                            torneos = torneos,
                            onEditarClick = { torneo ->
                                val intent = Intent(this@EventHomeActivity, TorneoEditActivity::class.java).apply {
                                    putExtra("torneo_id", torneo.id)
                                    putExtra("torneo_nombre", torneo.nombre)
                                    putExtra("torneo_deporte_nombre", torneo.deporte.nombre)
                                    putExtra("torneo_fecha", torneo.fecha)
                                    putExtra("torneo_hora", torneo.hora)
                                    putExtra("torneo_direccion", torneo.direccion)
                                }
                                startActivity(intent)
                            },
                            onEliminarClick = { torneo ->
                                AlertDialog.Builder(this@EventHomeActivity)
                                    .setTitle("Eliminar Torneo")
                                    .setMessage("¿Deseas eliminar el torneo ${torneo.nombre}?")
                                    .setPositiveButton("Eliminar") { _, _ ->
                                        eliminarTorneo(torneo.id)
                                    }
                                    .setNegativeButton("Cancelar", null)
                                    .show()
                            },
                            onItemClick = { torneo ->
                                val intent = Intent(this@EventHomeActivity, TorneoHomeActivity::class.java).apply {
                                    putExtra("torneo_id", torneo.id)
                                    putExtra("torneo_nombre", torneo.nombre)
                                    putExtra("torneo_deporte_nombre", torneo.deporte.nombre)
                                    putExtra("torneo_fecha", torneo.fecha)
                                    putExtra("torneo_hora", torneo.hora)
                                    putExtra("torneo_direccion", torneo.direccion)
                                }
                                startActivity(intent)
                            }
                        )
                    }
                } else {
                    Toast.makeText(this@EventHomeActivity, "Error al obtener torneos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EventHomeActivity, "Error de red", Toast.LENGTH_SHORT).show()
                Log.e("EventHomeActivity", "Error: ${e.message}", e)
            }
        }
    }


    private fun eliminarTorneo(torneoId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.eliminarTorneo(torneoId)
                if (response.isSuccessful) {
                    Toast.makeText(this@EventHomeActivity, "Torneo eliminado", Toast.LENGTH_SHORT).show()
                    obtenerTorneosPorEvento()
                } else {
                    Toast.makeText(this@EventHomeActivity, "No se pudo eliminar el torneo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EventHomeActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                Log.e("EventHomeActivity", "Error: ${e.message}", e)
            }
        }
    }
}
