package com.api.alquilaya.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.services.VehiculoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public List<VehiculoModel> getAllVehiculos(@RequestParam(required = false) String estado) {
        if (estado != null && !estado.isBlank()) {
            return vehiculoService.getVehiculosPorEstado(estado);
        }
        return vehiculoService.getAllVehiculos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoModel> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.getVehiculoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear vehículo
    @PostMapping
    public ResponseEntity<?> crearVehiculo(@Valid @RequestBody VehiculoModel vehiculo, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        VehiculoModel nuevoVehiculo = vehiculoService.saveVehiculo(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVehiculo);
    }

    // Actualizar vehículo
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Long id, @RequestBody VehiculoModel vehiculoActualizado) {
        return vehiculoService.updateVehiculo(id, vehiculoActualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar estado específico del vehículo
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String nuevoEstado = payload.get("estado");
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El campo 'estado' es requerido"));
        }
        boolean actualizado = vehiculoService.actualizarEstado(id, nuevoEstado);
        if (actualizado) {
            return ResponseEntity.ok(Map.of("mensaje", "Estado actualizado correctamente a: " + nuevoEstado));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar vehículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        boolean eliminado = vehiculoService.deleteVehiculo(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
