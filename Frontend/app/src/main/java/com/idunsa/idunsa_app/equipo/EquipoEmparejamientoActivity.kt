package com.idunsa.idunsa_app.equipo

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.encuentro.EncuentroExpandableListAdapter
import com.idunsa.idunsa_app.network.EncuentroResponse
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class EquipoEmparejamientoActivity : AppCompatActivity() {

    private lateinit var btnGenerarEmparejamiento: Button
    private var torneoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_emparejamiento)

        btnGenerarEmparejamiento = findViewById(R.id.btnGenerarEmparejamiento)

        torneoId = intent.getLongExtra("torneo_id", -1)

        if (torneoId != -1L) {
            verificarEncuentros(torneoId)
        } else {
            Toast.makeText(this, "ID de torneo inválido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verificarEncuentros(torneoId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerEncuentrosPorTorneo(torneoId)
                if (response.isSuccessful) {
                    val encuentros = response.body() ?: emptyList()
                    if (encuentros.isEmpty()) {
                        btnGenerarEmparejamiento.visibility = View.VISIBLE

                        btnGenerarEmparejamiento.setOnClickListener {
                            generarEmparejamientos(torneoId)
                        }
                    } else {
                        btnGenerarEmparejamiento.visibility = View.GONE
                        mostrarEncuentros(encuentros)
                    }
                } else {
                    Toast.makeText(this@EquipoEmparejamientoActivity, "Error al obtener encuentros", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EquipoEmparejamientoActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generarEmparejamientos(torneoId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.generarEncuentros(torneoId)
                if (response.isSuccessful) {
                    Toast.makeText(this@EquipoEmparejamientoActivity, "Emparejamientos generados correctamente", Toast.LENGTH_SHORT).show()
                    verificarEncuentros(torneoId)
                } else {
                    Toast.makeText(this@EquipoEmparejamientoActivity, "Error: los emparejamientos ya fueron generados o hubo un fallo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EquipoEmparejamientoActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun mostrarEncuentros(encuentros: List<EncuentroResponse>) {
        val expandableListView = findViewById<ExpandableListView>(R.id.expandableListViewEncuentros)

        val encuentrosPorNombreRonda = encuentros.groupBy { it.nombreRonda }

        val headers = encuentrosPorNombreRonda.keys.sortedBy { nombre ->
            when (nombre) {
                "Fase Preliminar" -> 0
                "Ronda 1" -> 1
                "Cuartos de Final" -> 2
                "Semifinal" -> 3
                "Final" -> 4
                else -> 99
            }
        }

        val dataMap = headers.associateWith { nombre ->
            encuentrosPorNombreRonda[nombre] ?: emptyList()
        }

        val adapter = EncuentroExpandableListAdapter(this, headers, dataMap)
        expandableListView.setAdapter(adapter)

        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            false
        }

        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            true
        }
    }


}
