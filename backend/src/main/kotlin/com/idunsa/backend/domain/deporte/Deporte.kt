package com.idunsa.backend.domain.deporte

import jakarta.persistence.*

@Entity
@Table(name = "deporte")
data class Deporte(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val nombre: String = "",

    @Column(nullable = false)
    val jugadores: Int = 0,

    @Column(nullable = false)
    val suplentes: Int = 0,

    @Column(nullable = false)
    val tipo: String = ""
)
