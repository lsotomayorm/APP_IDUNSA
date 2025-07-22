package com.idunsa.backend.repository

import com.idunsa.backend.domain.usuario.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByCui(cui: Int ): Usuario?
}
