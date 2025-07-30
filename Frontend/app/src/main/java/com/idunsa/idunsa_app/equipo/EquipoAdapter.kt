package com.idunsa.idunsa_app.equipo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.*

class EquipoAdapter(
    private val equipos: List<EquipoResponse>,
    private val onItemClick: (EquipoResponse) -> Unit
) : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {

    inner class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreEquipo)

        fun bind(equipo: EquipoResponse) {
            tvNombre.text = equipo.nombre
            itemView.setOnClickListener {
                onItemClick(equipo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipo, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        holder.bind(equipos[position])
    }

    override fun getItemCount(): Int = equipos.size
}


