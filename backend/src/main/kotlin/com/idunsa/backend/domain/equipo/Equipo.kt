package com.idunsa.backend.domain.equipo

import com.idunsa.backend.domain.torneo.Torneo
import com.idunsa.backend.domain.usuario.Usuario
import jakarta.persistence.*

@Entity
data class Equipo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var nombre: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "torneo_id", nullable = false)
    var torneo: Torneo? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capitan_id", nullable = false)
    var capitan: Usuario? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "equipo_usuarios",
        joinColumns = [JoinColumn(name = "equipo_id")],
        inverseJoinColumns = [JoinColumn(name = "usuario_id")]
    )
    var integrantes: MutableList<Usuario> = mutableListOf()
)
