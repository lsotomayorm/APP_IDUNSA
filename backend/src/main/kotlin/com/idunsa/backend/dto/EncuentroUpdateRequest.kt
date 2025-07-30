package com.idunsa.backend.dto

data class EncuentroUpdateRequest(
    val score1: Float?, 
    val score2: Float?,
    val ganador: String?,
    val estado: String
)
