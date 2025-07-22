package com.idunsa.idunsa_app.torneo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.Torneo
import android.content.Intent
import android.content.Context


class TorneoAdapter(
    private val torneos: List<Torneo>,
    private val onEditarClick: (Torneo) -> Unit,
    private val onEliminarClick: (Torneo) -> Unit,
    private val onItemClick: (Torneo) -> Unit
) : RecyclerView.Adapter<TorneoAdapter.TorneoViewHolder>() {

    inner class TorneoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombreTorneo)
        val tipo: TextView = itemView.findViewById(R.id.tipoTorneo)
        val fecha: TextView = itemView.findViewById(R.id.fechaTorneo)
        val direccion: TextView = itemView.findViewById(R.id.direccionTorneo)
        val hora: TextView = itemView.findViewById(R.id.horaTorneo)
        val editarButton: Button = itemView.findViewById(R.id.editarButton)
        val eliminarButton: Button = itemView.findViewById(R.id.eliminarButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TorneoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_torneo, parent, false)
        return TorneoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TorneoViewHolder, position: Int) {
        val torneo = torneos[position]
        holder.nombre.text = torneo.nombre
        holder.tipo.text = "Deporte: ${torneo.deporte.nombre}"
        holder.fecha.text = "Fecha: ${torneo.fecha}"
        holder.direccion.text = "Dirección: ${torneo.direccion}"
        holder.hora.text = "Hora: ${torneo.hora}"

        holder.editarButton.setOnClickListener { onEditarClick(torneo) }
        holder.eliminarButton.setOnClickListener { onEliminarClick(torneo) }
        holder.itemView.setOnClickListener { onItemClick(torneo) }
    }

    override fun getItemCount(): Int = torneos.size
}


