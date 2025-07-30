package com.idunsa.idunsa_app.equipo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.EquipoResponse
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class EquipoListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var equipoAdapter: EquipoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_list)

        recyclerView = findViewById(R.id.recyclerViewEquipos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val torneoId = intent.getLongExtra("torneo_id", -1)

        if (torneoId != -1L) {
            obtenerEquipos(torneoId)
        } else {
            Log.e("EquipoListActivity", "ID de torneo inválido")
        }
    }

    private fun obtenerEquipos(torneoId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerEquiposPorTorneo(torneoId)
                if (response.isSuccessful) {
                    val equipos = response.body() ?: emptyList()
                    mostrarEquipos(equipos)
                } else {
                    Log.e("EquipoListActivity", "Error en la respuesta: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("EquipoListActivity", "Excepción al obtener equipos", e)
            }
        }
    }

    private fun mostrarEquipos(equipos: List<EquipoResponse>) {
        equipoAdapter = EquipoAdapter(equipos) { equipo ->
            val intent = Intent(this, EquipoDetalleActivity::class.java).apply {
                putExtra("equipo_nombre", equipo.nombre)
                putExtra("capitan", equipo.capitan)
                putStringArrayListExtra("integrantes", ArrayList(equipo.integrantes))
            }
            startActivity(intent)
        }
        recyclerView.adapter = equipoAdapter
    }

}
