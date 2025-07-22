package com.idunsa.backend.service

import com.idunsa.backend.repository.UsuarioRepository
import com.idunsa.backend.dto.*
import com.idunsa.backend.domain.usuario.Usuario
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun login(request: LoginRequest): ResponseEntity<Any> {
        val usuario = usuarioRepository.findByCui(request.cui)
        return if (usuario != null && usuario.password == request.password) {
            ResponseEntity.ok(usuario) // Idealmente usar un DTO aquí
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos")
        }
    }

    fun registrar(request: RegistroRequest): ResponseEntity<Any> {
        if (usuarioRepository.findByCui(request.cui) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CUI ya registrado")
        }

        val nuevoUsuario = Usuario(
            cui = request.cui,
            nombre = request.nombre,
            apellido = request.apellido,
            email = request.email,
            password = request.password,
            escuela = request.escuela,
            rol = "ESTUDIANTE"
        )

        val guardado = usuarioRepository.save(nuevoUsuario)
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado)
    }

}
