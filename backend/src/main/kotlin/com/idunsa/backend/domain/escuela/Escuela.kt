package com.idunsa.backend.domain.escuela

import jakarta.persistence.*

@Entity
@Table(name = "escuela")
data class Escuela(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, unique = true)
    val nombre: String = ""
)
