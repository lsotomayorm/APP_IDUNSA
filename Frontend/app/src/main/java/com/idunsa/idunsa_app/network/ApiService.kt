package com.idunsa.idunsa_app.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- Autenticación y Registro ---
    @POST("api/usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<Usuario>

    @POST("api/usuarios/registro")
    fun registrar(@Body registroRequest: RegistroRequest): Call<Usuario>

}
