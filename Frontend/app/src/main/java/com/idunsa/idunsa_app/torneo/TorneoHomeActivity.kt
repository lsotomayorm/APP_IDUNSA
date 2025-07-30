package com.idunsa.idunsa_app.torneo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.reglamento.ReglamentoHomeActivity
import androidx.lifecycle.lifecycleScope
import com.idunsa.idunsa_app.equipo.EquipoAddActivity
import com.idunsa.idunsa_app.equipo.EquipoEmparejamientoActivity
import com.idunsa.idunsa_app.equipo.EquipoListActivity
import com.idunsa.idunsa_app.network.DeporteResponse
import com.idunsa.idunsa_app.network.RetrofitClient
import kotlinx.coroutines.launch

class TorneoHomeActivity : AppCompatActivity() {

    private lateinit var tvTitulo: TextView
    private lateinit var tvDetalles: TextView
    private lateinit var btnAtras: Button
    private lateinit var btnAgregarEquipo: Button
    private lateinit var btnListaEquipos: Button
    private lateinit var btnEmparejamientos: Button
    private lateinit var btnResultados: Button


    private var torneoId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_torneo_home)

        tvTitulo = findViewById(R.id.tvTitulo)
        tvDetalles = findViewById(R.id.tvDetalles)
        btnAtras = findViewById(R.id.btnAtras)

        btnAgregarEquipo = findViewById(R.id.btnAgregarEquipo)
        btnListaEquipos = findViewById(R.id.btnListaEquipos)
        btnEmparejamientos = findViewById(R.id.btnEmparejamientos)
        btnResultados = findViewById(R.id.btnVerResultados)


        torneoId = intent.getLongExtra("torneo_id", 0L)
        val nombre = intent.getStringExtra("torneo_nombre") ?: ""
        val tipoDeporte = intent.getStringExtra("torneo_deporte_nombre") ?: ""
        val fecha = intent.getStringExtra("torneo_fecha") ?: ""
        val hora = intent.getStringExtra("torneo_hora") ?: ""
        val direccion = intent.getStringExtra("torneo_direccion") ?: ""


        tvTitulo.text = nombre
        tvDetalles.text = "$tipoDeporte\n $fecha | $hora\nDirección: $direccion"

        obtenerTipoDeporte(tipoDeporte)

        findViewById<Button>(R.id.btnVerReglamento).setOnClickListener {
            if (torneoId != 0L) {
                val intent = Intent(this, ReglamentoHomeActivity::class.java)
                intent.putExtra("torneo_id", torneoId)
                Log.d("DEBUG", "Enviando torneo_id = $torneoId")
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error: ID del torneo no válido", Toast.LENGTH_SHORT).show()
            }

        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun obtenerTipoDeporte(nombreDeporte: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerDeportePorNombre(nombreDeporte)
                if (response.isSuccessful) {
                    val deporte: DeporteResponse? = response.body()
                    if (deporte != null) {
                        if (deporte.tipo == "Individual") {
                            btnAgregarEquipo.text = "Inscribirse"
                            btnListaEquipos.text = "Lista de Participantes"
                        } else {
                            btnAgregarEquipo.text = "Inscribir Equipo"
                            btnListaEquipos.text = "Lista de Equipos"
                        }

                        btnAgregarEquipo.setOnClickListener {
                            val intent = Intent(this@TorneoHomeActivity, EquipoAddActivity::class.java).apply {
                                putExtra("torneo_id", torneoId)
                                putExtra("deporte_jugadores", deporte.jugadores)
                                putExtra("deporte_suplentes", deporte.suplentes)
                            }
                            startActivity(intent)
                        }

                        btnListaEquipos.setOnClickListener {
                            val intent = Intent(this@TorneoHomeActivity, EquipoListActivity::class.java).apply {
                                putExtra("torneo_id", torneoId)
                            }
                            startActivity(intent)
                        }

                        btnEmparejamientos.setOnClickListener  {
                            val intent = Intent(this@TorneoHomeActivity, EquipoEmparejamientoActivity::class.java).apply {
                                putExtra("torneo_id", torneoId)
                            }
                            startActivity(intent)
                        }

                        btnResultados.setOnClickListener  {
                            val intent = Intent(this@TorneoHomeActivity, TorneoResultadoActivity::class.java).apply {
                                putExtra("torneo_id", torneoId)
                            }
                            startActivity(intent)
                        }

                    } else {
                        Toast.makeText(this@TorneoHomeActivity, "No se encontró el deporte", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@TorneoHomeActivity, "Error al obtener deporte", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@TorneoHomeActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
