package com.api.alquilaya.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.api.alquilaya.models.MantenimientoModel;
import com.api.alquilaya.services.MantenimientoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @GetMapping
    public List<MantenimientoModel> getAllMantenimientos() {
        return mantenimientoService.getAllMantenimientos();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoModel> getMantenimientoById(@PathVariable Long id) {
        return mantenimientoService.getMantenimientoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Registrar mantenimiento
    @PostMapping
    public ResponseEntity<?> crearMantenimiento(@Valid @RequestBody MantenimientoModel mantenimiento, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            MantenimientoModel nuevo = mantenimientoService.crearMantenimiento(mantenimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Finalizar mantenimiento (libera vehículo a Disponible)
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Long id) {
        boolean finalizado = mantenimientoService.finalizarMantenimiento(id);
        if (finalizado) {
            return ResponseEntity.ok(Map.of("mensaje", "Mantenimiento finalizado con éxito. Vehículo disponible."));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMantenimiento(@PathVariable Long id) {
        boolean eliminado = mantenimientoService.deleteMantenimiento(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
