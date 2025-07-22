package com.idunsa.backend.controller

import com.idunsa.backend.dto.evento.EventoRequestDTO
import com.idunsa.backend.dto.evento.EventoResponseDTO
import com.idunsa.backend.servicio.EventoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = ["*"])
class EventoController(private val eventoService: EventoService) {

    @GetMapping
    fun obtenerEventos(): List<EventoResponseDTO> {
        return eventoService.listarEventos()
    }

    @PostMapping
    fun crearEvento(@RequestBody dto: EventoRequestDTO): EventoResponseDTO {
        return eventoService.crearEvento(dto)
    }

    @DeleteMapping("/{id}")
    fun eliminarEvento(@PathVariable id: Long) {
        eventoService.eliminarEvento(id)
    }

    @PutMapping("/{id}")
    fun actualizarEvento(
        @PathVariable id: Long,
        @RequestBody dto: EventoRequestDTO
    ): EventoResponseDTO {
        return eventoService.actualizarEvento(id, dto)
    }

}
