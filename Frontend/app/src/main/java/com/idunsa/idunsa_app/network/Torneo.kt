package com.idunsa.idunsa_app.network

data class Torneo(
    val id: Long,
    val nombre: String,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val eventoId: Long,
    val reglamento: String?,
    val deporte: Deporte
)