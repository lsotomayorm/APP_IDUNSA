package com.idunsa.backend.domain.usuario

import jakarta.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(

    @Id
    @Column(name = "cui", nullable = false, unique = true)
    val cui: Int = 0,

    @Column(name = "nombre", nullable = false)
    val nombre: String = "",

    @Column(name = "apellido", nullable = false)
    val apellido: String = "",

    @Column(name = "email", nullable = false, unique = true)
    val email: String = "",

    @Column(name = "password", nullable = false)
    val password: String = "",

    @Column(name = "escuela")
    val escuela: String = "",

    @Column(name = "rol", nullable = false)
    val rol: String = "ESTUDIANTE"
)


