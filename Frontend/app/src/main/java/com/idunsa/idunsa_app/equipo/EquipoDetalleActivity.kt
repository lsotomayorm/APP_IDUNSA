package com.idunsa.idunsa_app.equipo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idunsa.idunsa_app.R

class EquipoDetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_detalle)

        // Obtener los datos del intent
        val nombre = intent.getStringExtra("equipo_nombre") ?: "Sin nombre"
        val capitan = intent.getStringExtra("capitan") ?: "Sin capitán"
        val integrantes = intent.getStringArrayListExtra("integrantes") ?: arrayListOf()

        // Obtener las vistas
        val tvTitulo = findViewById<TextView>(R.id.tvDetalleEquipoNombre)
        val tvNombre = findViewById<TextView>(R.id.tvNombreEquipo)
        val tvCapitan = findViewById<TextView>(R.id.tvCapitanEquipo)
        val tvIntegrantes = findViewById<TextView>(R.id.tvIntegrantesEquipo)

        // Asignar valores
        tvTitulo.text = "Detalles del Equipo"
        tvNombre.text = "Nombre: $nombre"
        tvCapitan.text = "Capitán: $capitan"
        tvIntegrantes.text = "Integrantes:\n${integrantes.joinToString(separator = "\n")}"
    }
}