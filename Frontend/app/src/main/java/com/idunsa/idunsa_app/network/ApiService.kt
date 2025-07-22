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

    // --- Escuelas ---
    @GET("api/escuelas")
    fun obtenerEscuelas(): Call<List<Escuela>>

    // --- Eventos ---
    @GET("api/eventos")
    fun obtenerEventos(): Call<List<Evento>>

    @POST("api/eventos")
    fun crearEvento(@Body eventoRequest: EventoRequest): Call<Void>

    @PUT("api/eventos/{id}")
    fun actualizarEvento(@Path("id") id: Long, @Body eventoRequest: EventoRequest): Call<Void>

    @DELETE("api/eventos/{id}")
    fun eliminarEvento(@Path("id") id: Long): Call<Void>

}
