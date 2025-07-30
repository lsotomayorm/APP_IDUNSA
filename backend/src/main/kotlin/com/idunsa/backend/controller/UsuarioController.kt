package com.idunsa.backend.controller

import com.idunsa.backend.dto.*
import com.idunsa.backend.domain.usuario.Usuario
import com.idunsa.backend.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        return usuarioService.login(request)
    }
    @PostMapping("/registro")
    fun registrar(@RequestBody request: RegistroRequest): ResponseEntity<Any> {
        return usuarioService.registrar(request)
    }
}
