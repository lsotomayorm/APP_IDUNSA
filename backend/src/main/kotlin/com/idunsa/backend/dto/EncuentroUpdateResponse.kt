package com.idunsa.backend.dto

data class EncuentroUpdateResponse(
    val id: Long,
    val estado: String,
    val score1: Float?,
    val score2: Float?,
    val ganador: Long?
)
