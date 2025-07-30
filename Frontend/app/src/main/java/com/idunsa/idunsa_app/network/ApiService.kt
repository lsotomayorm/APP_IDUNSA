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

    // --- Deportes ---
    @GET("/api/deportes")
    suspend fun obtenerDeportes(): Response<List<Deporte>>

    @GET("/api/deportes/buscar")
    suspend fun obtenerDeportePorNombre(
        @Query("nombre") nombre: String
    ): Response<DeporteResponse>

    // --- Torneos ---
    @POST("/api/torneos")
    suspend fun crearTorneo(@Body torneo: TorneoRequest): Response<TorneoResponse>

    @GET("/api/torneos/evento/{eventoId}")
    suspend fun obtenerTorneosPorEvento(@Path("eventoId") eventoId: Long): Response<List<TorneoResponse>>

    @DELETE("/api/torneos/{id}")
    suspend fun eliminarTorneo(@Path("id") id: Long): Response<Void>


    @PUT("/api/torneos/{id}")
    suspend fun actualizarTorneo(
        @Path("id") id: Long,
        @Body torneo: TorneoRequest
    ): Response<TorneoResponse>

    @GET("/api/torneos/{id}")
    suspend fun obtenerTorneoPorId(@Path("id") torneoId: Long): Response<TorneoResponse>

    @PATCH("api/torneos/{id}/reglamento")
    suspend fun actualizarReglamento(
        @Path("id") id: Long,
        @Body body: Map<String, String?>
    ): Response<TorneoResponse>

    // --- Equipos ---

    @POST("/api/equipos")
    suspend fun crearEquipo(@Body request: EquipoRequest): Response<EquipoResponse>

    @GET("/api/equipos/torneo/{torneoId}")
    suspend fun obtenerEquiposPorTorneo(@Path("torneoId") torneoId: Long): Response<List<EquipoResponse>>

    // --- Encuentros ---

    @GET("/api/encuentros/torneo/{torneoId}")
    suspend fun obtenerEncuentrosPorTorneo(@Path("torneoId") torneoId: Long): Response<List<EncuentroResponse>>

    @POST("/api/encuentros/torneo/{torneoId}/generar")
    suspend fun generarEncuentros(@Path("torneoId") torneoId: Long): Response<Void>


    @GET("/api/encuentros/{id}")
    suspend fun obtenerEncuentroPorId(@Path("id") id: Long): Response<EncuentroResponse>


    @PUT("/api/encuentros/{id}")
    suspend fun actualizarEncuentro(
        @Path("id") id: Long,
        @Body encuentro: EncuentroUpdateRequest
    ): Response<EncuentroResponse>

}
