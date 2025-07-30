package com.idunsa.idunsa_app.evento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.Evento

class EventsAdapter(
    private val eventos: List<Evento>,
    private val esAdmin: Boolean,
    private val onEditarClick: (Evento) -> Unit,
    private val onEliminarClick: (Evento) -> Unit,
    private val onItemClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventoViewHolder>() {

    inner class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombreEvento)
        val fechaInicio: TextView = itemView.findViewById(R.id.fechaInicio)
        val fechaFin: TextView = itemView.findViewById(R.id.fechaFin)
        val editarButton: Button = itemView.findViewById(R.id.editarButton)
        val eliminarButton: Button = itemView.findViewById(R.id.eliminarButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]
        holder.nombre.text = evento.nombre
        holder.fechaInicio.text = "Inicio: ${evento.fechaInicio}"
        holder.fechaFin.text = "Fin: ${evento.fechaFin}"

        if (esAdmin) {
            holder.editarButton.visibility = View.VISIBLE
            holder.eliminarButton.visibility = View.VISIBLE
            holder.editarButton.setOnClickListener { onEditarClick(evento) }
            holder.eliminarButton.setOnClickListener { onEliminarClick(evento) }
        } else {
            holder.editarButton.visibility = View.GONE
            holder.eliminarButton.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { onItemClick(evento) }
    }

    override fun getItemCount(): Int = eventos.size
}

