package com.idunsa.idunsa_app.equipo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.idunsa.idunsa_app.R

class EquipoDetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_detalle)

        val nombre = intent.getStringExtra("equipo_nombre") ?: "Sin nombre"
        val capitan = intent.getStringExtra("capitan") ?: "Sin capitán"
        val integrantes = intent.getStringArrayListExtra("integrantes") ?: arrayListOf()

        val tvTitulo = findViewById<TextView>(R.id.tvDetalleEquipoNombre)
        val tvNombre = findViewById<TextView>(R.id.tvNombreEquipo)
        val tvCapitan = findViewById<TextView>(R.id.tvCapitanEquipo)
        val tvIntegrantes = findViewById<TextView>(R.id.tvIntegrantesEquipo)

        tvTitulo.text = "Detalles del Equipo"
        tvNombre.text = "Nombre: $nombre"
        tvCapitan.text = "Capitán: $capitan"
        tvIntegrantes.text = "Integrantes:\n${integrantes.joinToString(separator = "\n")}"
    }
}