package com.idunsa.backend.dto

data class EncuentroResponse(
    val id: Long,
    val equipo1: String,
    val equipo2: String,
    val ronda: Int,
    var nombreRonda: String,
    val score1: Float?,
    val score2: Float?,
    val ganador: String?,
    val fecha: String?,
    val hora: String?,
    val estado: String
)