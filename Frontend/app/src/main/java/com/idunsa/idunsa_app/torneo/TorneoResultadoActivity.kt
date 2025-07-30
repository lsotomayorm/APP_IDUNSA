package com.idunsa.idunsa_app.torneo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.encuentro.EncuentroEditActivity
import com.idunsa.idunsa_app.encuentro.ResultadoExpandableListAdapter
import com.idunsa.idunsa_app.network.EncuentroResponse
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class TorneoResultadoActivity : AppCompatActivity() {

    private var torneoId: Long = -1
    private var esAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_torneo_resultado)

        val sharedPref = getSharedPreferences("MiAppPrefs", MODE_PRIVATE)
        val rolUsuario = sharedPref.getString("usuario_rol", "")
        esAdmin = rolUsuario == "ADMIN"

        torneoId = intent.getLongExtra("torneo_id", -1)
    }

    private fun verificarEncuentros(torneoId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerEncuentrosPorTorneo(torneoId)
                if (response.isSuccessful) {
                    val encuentros = response.body() ?: emptyList()
                    if (encuentros.isEmpty()) {
                        mostrarMensajeNoGenerados()
                    } else {
                        mostrarEncuentros(encuentros)
                    }
                } else {
                    Toast.makeText(this@TorneoResultadoActivity, "Error al obtener encuentros", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@TorneoResultadoActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarMensajeNoGenerados() {
        val expandableListView = findViewById<ExpandableListView>(R.id.expandableListViewResultados)
        val mensajeTextView = findViewById<TextView>(R.id.mensajeNoGenerados)

        expandableListView.visibility = View.GONE
        mensajeTextView.visibility = View.VISIBLE
    }

    private fun mostrarEncuentros(encuentros: List<EncuentroResponse>) {
        val expandableListView = findViewById<ExpandableListView>(R.id.expandableListViewResultados)
        val mensajeTextView = findViewById<TextView>(R.id.mensajeNoGenerados)

        mensajeTextView.visibility = View.GONE
        expandableListView.visibility = View.VISIBLE

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

        val adapter = ResultadoExpandableListAdapter(this, headers, dataMap)
        expandableListView.setAdapter(adapter)

        expandableListView.setOnGroupClickListener { _, _, _, _ -> false }

        expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            if (!esAdmin) return@setOnChildClickListener true

            val encuentro = dataMap[headers[groupPosition]]?.get(childPosition)
            if (encuentro != null) {
                val intent = Intent(this@TorneoResultadoActivity, EncuentroEditActivity::class.java)
                intent.putExtra("encuentro_id", encuentro.id)
                intent.putExtra("equipo1_nombre", encuentro.equipo1)
                intent.putExtra("equipo2_nombre", encuentro.equipo2)
                startActivity(intent)
            }
            true
        }
    }
    override fun onResume() {
        super.onResume()
        if (torneoId != -1L) {
            verificarEncuentros(torneoId)
        }
    }

}
