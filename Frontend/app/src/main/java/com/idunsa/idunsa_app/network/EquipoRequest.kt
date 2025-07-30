package com.idunsa.idunsa_app.network

data class EquipoRequest(
    val nombre: String,
    val torneoId: Long,
    val capitanId: Int,
    val integranteIds: List<Int>
)
