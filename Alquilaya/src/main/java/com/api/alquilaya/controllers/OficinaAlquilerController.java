package com.api.alquilaya.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.alquilaya.models.OficinaAlquilerModel;
import com.api.alquilaya.services.OficinaAlquilerService;

@RestController
@RequestMapping("/api/oficinas")
public class OficinaAlquilerController {

    private final OficinaAlquilerService oficinaService;

    public OficinaAlquilerController(OficinaAlquilerService oficinaService) {
        this.oficinaService = oficinaService;
    }

    // Obtener todas las oficinas
    @GetMapping
    public List<OficinaAlquilerModel> getAllOficinas() {
        return oficinaService.getAllOficinas();
    }

    // Obtener oficina por ID
    @GetMapping("/{id}")
    public ResponseEntity<OficinaAlquilerModel> getOficinaById(@PathVariable Long id) {
        return oficinaService.getOficinaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Filtrar oficinas por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public List<OficinaAlquilerModel> getOficinasPorCiudad(@PathVariable String ciudad) {
        return oficinaService.getOficinasPorCiudad(ciudad);
    }

    // Crear oficina
    @PostMapping
    public ResponseEntity<OficinaAlquilerModel> crearOficina(@RequestBody OficinaAlquilerModel oficina) {
        OficinaAlquilerModel nuevaOficina = oficinaService.saveOficina(oficina);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOficina);
    }

    // Actualizar oficina
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOficina(@PathVariable Long id, @RequestBody OficinaAlquilerModel oficinaActualizada) {
        return oficinaService.updateOficina(id, oficinaActualizada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOficina(@PathVariable Long id) {
        boolean eliminado = oficinaService.deleteOficina(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
