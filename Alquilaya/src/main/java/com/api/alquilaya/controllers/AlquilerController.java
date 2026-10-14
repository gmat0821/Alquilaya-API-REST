package com.api.alquilaya.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.api.alquilaya.models.AlquilerModel;
import com.api.alquilaya.services.AlquilerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // Listar todos los alquileres
    @GetMapping
    public List<AlquilerModel> getAllAlquileres() {
        return alquilerService.getAllAlquileres();
    }

    // Obtener alquiler por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlquilerModel> getAlquilerById(@PathVariable Long id) {
        return alquilerService.getAlquilerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Registrar nuevo alquiler
    @PostMapping
    public ResponseEntity<?> crearAlquiler(@Valid @RequestBody AlquilerModel alquiler, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            AlquilerModel nuevoAlquiler = alquilerService.crearAlquiler(alquiler);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlquiler);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint para devolver vehículo y cerrar alquiler
    @PostMapping("/{id}/devolver")
    public ResponseEntity<?> devolverVehiculo(@PathVariable Long id) {
        try {
            AlquilerModel alquilerActualizado = alquilerService.devolverVehiculo(id);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Vehículo devuelto exitosamente. Contrato completado.",
                    "alquiler", alquilerActualizado
            ));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    // Eliminar contrato de alquiler
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlquiler(@PathVariable Long id) {
        try {
            boolean eliminado = alquilerService.deleteAlquiler(id);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
