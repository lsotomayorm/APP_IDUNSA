package com.idunsa.idunsa_app.network

data class TorneoRequest(
    val nombre: String,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val eventoId: Long,
    val deporteId: Int
)
