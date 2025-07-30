package com.idunsa.idunsa_app.encuentro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.EncuentroResponse
import androidx.core.content.ContextCompat
import android.graphics.Typeface


class ResultadoExpandableListAdapter(
    private val context: Context,
    private val titles: List<String>,
    private val data: Map<String, List<EncuentroResponse>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = titles.size

    override fun getChildrenCount(groupPosition: Int): Int =
        data[titles[groupPosition]]?.size ?: 0

    override fun getGroup(groupPosition: Int): Any = titles[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val ronda = titles[groupPosition]
        return data[ronda]?.get(childPosition) ?: EncuentroResponse(
            id = 0L,
            equipo1 = "",
            equipo2 = "",
            ronda = 0,
            nombreRonda = "",
            score1 = 0f,
            score2 = 0f,
            ganador = "",
            fecha = "",
            hora = "",
            estado = ""
        )
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        val text = view.findViewById<TextView>(android.R.id.text1)
        text.text = getGroup(groupPosition) as String
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_encuentro, parent, false)

        val encuentro = getChild(groupPosition, childPosition) as EncuentroResponse

        val textEquipos = view.findViewById<TextView>(R.id.textEquipos)
        val textFechaHora = view.findViewById<TextView>(R.id.textFechaHora)
        val textResultado = view.findViewById<TextView>(R.id.textResultado)
        val textEstado = view.findViewById<TextView>(R.id.textEstado)
        val textGanador = view.findViewById<TextView>(R.id.textGanador)

        val score1 = (encuentro.score1 ?: 0f).toInt()
        val score2 = (encuentro.score2 ?: 0f).toInt()


        val ganador = encuentro.ganador?.takeIf { it.isNotBlank() } ?: ""


        textEquipos.text = "${encuentro.equipo1} vs ${encuentro.equipo2}"
        textFechaHora.text = "${encuentro.fecha} ${encuentro.hora?.substring(0, 5) ?: "??:??"}"
        textResultado.text = "${score1} : ${score2}"
        textEstado.text = "Estatus: ${encuentro.estado}"

        when (encuentro.estado) {
            "PENDIENTE" -> {
                textEstado.setTextColor(ContextCompat.getColor(context, R.color.orange))
                textEstado.setTypeface(null, Typeface.BOLD)
            }
            "EN_CURSO" -> {
                textEstado.setTextColor(ContextCompat.getColor(context, R.color.green))
                textEstado.setTypeface(null, Typeface.BOLD)
            }
            "FINALIZADO" -> {
                textEstado.setTextColor(ContextCompat.getColor(context, R.color.red))
                textEstado.setTypeface(null, Typeface.BOLD)
            }
        }

        textGanador.text = "Ganador: $ganador"

        return view
    }

}
