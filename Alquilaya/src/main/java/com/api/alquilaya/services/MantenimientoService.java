package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.alquilaya.models.MantenimientoModel;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IMantenimientoRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class MantenimientoService {

    private final IMantenimientoRepository mantenimientoRepository;
    private final IVehiculoRepository vehiculoRepository;

    public MantenimientoService(IMantenimientoRepository mantenimientoRepository, IVehiculoRepository vehiculoRepository) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<MantenimientoModel> getAllMantenimientos() {
        return mantenimientoRepository.findAll();
    }

    public Optional<MantenimientoModel> getMantenimientoById(Long id) {
        return mantenimientoRepository.findById(id);
    }

    @Transactional
    public MantenimientoModel crearMantenimiento(MantenimientoModel mantenimiento) {
        if (mantenimiento.getVehiculo() == null || mantenimiento.getVehiculo().getIdVehiculo() == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio para programar mantenimiento");
        }

        VehiculoModel vehiculo = vehiculoRepository.findById(mantenimiento.getVehiculo().getIdVehiculo())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + mantenimiento.getVehiculo().getIdVehiculo()));

        mantenimiento.setVehiculo(vehiculo);
        vehiculo.setEstado("Mantenimiento");
        vehiculoRepository.save(vehiculo);

        return mantenimientoRepository.save(mantenimiento);
    }

    @Transactional
    public boolean finalizarMantenimiento(Long id) {
        Optional<MantenimientoModel> mantOpt = mantenimientoRepository.findById(id);
        if (mantOpt.isPresent()) {
            MantenimientoModel mant = mantOpt.get();
            VehiculoModel vehiculo = mant.getVehiculo();
            if (vehiculo != null) {
                vehiculo.setEstado("Disponible");
                vehiculoRepository.save(vehiculo);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteMantenimiento(Long id) {
        Optional<MantenimientoModel> mantOpt = mantenimientoRepository.findById(id);
        if (mantOpt.isPresent()) {
            MantenimientoModel mant = mantOpt.get();
            VehiculoModel vehiculo = mant.getVehiculo();
            if (vehiculo != null && "Mantenimiento".equalsIgnoreCase(vehiculo.getEstado())) {
                vehiculo.setEstado("Disponible");
                vehiculoRepository.save(vehiculo);
            }
            mantenimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
