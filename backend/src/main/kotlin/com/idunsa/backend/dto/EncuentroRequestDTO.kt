package com.idunsa.backend.dto

data class EncuentroRequest(
    val scoreEquipo1: Float?, 
    val scoreEquipo2: Float?,
    val ganador: String?,
    val estado: String
)
