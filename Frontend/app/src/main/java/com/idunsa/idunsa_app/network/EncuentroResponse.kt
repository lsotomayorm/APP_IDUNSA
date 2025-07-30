package com.idunsa.idunsa_app.network

data class EncuentroResponse(
    val id: Long,
    val equipo1: String,
    val equipo2: String,
    val ronda: Int,
    val nombreRonda: String,
    val score1: Float?,
    val score2: Float?,
    val ganador: String?,
    val fecha: String?,
    val hora: String?,
    val estado: String
)