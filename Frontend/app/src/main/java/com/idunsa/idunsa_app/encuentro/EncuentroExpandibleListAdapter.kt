package com.idunsa.idunsa_app.encuentro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.idunsa.idunsa_app.R
import com.idunsa.idunsa_app.network.EncuentroResponse

class EncuentroExpandableListAdapter(
    private val context: Context,
    private val titles: List<String>,
    private val data: Map<String, List<EncuentroResponse>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = titles.size
    override fun getChildrenCount(groupPosition: Int): Int = data[titles[groupPosition]]?.size ?: 0
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
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        val text = view.findViewById<TextView>(android.R.id.text1)
        text.text = getGroup(groupPosition) as String
        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val encuentro = getChild(groupPosition, childPosition) as EncuentroResponse
        val text = view.findViewById<TextView>(android.R.id.text1)
        text.text = "${encuentro.equipo1} vs ${encuentro.equipo2}\n${encuentro.fecha} ${encuentro.hora?.substring(0, 5)}"
        return view
    }
}