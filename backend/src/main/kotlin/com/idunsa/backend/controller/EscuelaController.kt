package com.idunsa.backend.controller

import com.idunsa.backend.repository.EscuelaRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/escuelas")
class EscuelaController(private val escuelaRepo: EscuelaRepository) {

    @GetMapping
    fun listarEscuelas() = escuelaRepo.findAllOrderByNombreAsc()

}
