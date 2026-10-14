package com.api.alquilaya.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.alquilaya.models.AlquilerModel;
import com.api.alquilaya.models.ClienteModel;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IAlquilerRepository;
import com.api.alquilaya.repositories.IClienteRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class AlquilerService {

    private final IAlquilerRepository alquilerRepository;
    private final IVehiculoRepository vehiculoRepository;
    private final IClienteRepository clienteRepository;

    public AlquilerService(IAlquilerRepository alquilerRepository,
                           IVehiculoRepository vehiculoRepository,
                           IClienteRepository clienteRepository) {
        this.alquilerRepository = alquilerRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<AlquilerModel> getAllAlquileres() {
        return alquilerRepository.findAll();
    }

    public Optional<AlquilerModel> getAlquilerById(Long id) {
        return alquilerRepository.findById(id);
    }

    @Transactional
    public AlquilerModel crearAlquiler(AlquilerModel alquiler) {
        if (alquiler.getFechaAlquiler() == null || alquiler.getFechaDevolucion() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y devolución son obligatorias");
        }

        if (alquiler.getCliente() == null || alquiler.getCliente().getIdCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio para registrar el alquiler");
        }

        if (alquiler.getVehiculo() == null || alquiler.getVehiculo().getIdVehiculo() == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio para registrar el alquiler");
        }

        ClienteModel cliente = clienteRepository.findById(alquiler.getCliente().getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + alquiler.getCliente().getIdCliente()));

        VehiculoModel vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getIdVehiculo())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + alquiler.getVehiculo().getIdVehiculo()));

        if (!"Disponible".equalsIgnoreCase(vehiculo.getEstado())) {
            throw new IllegalStateException("El vehículo no está disponible para alquilar. Estado actual: " + vehiculo.getEstado());
        }

        long dias = ChronoUnit.DAYS.between(alquiler.getFechaAlquiler(), alquiler.getFechaDevolucion());
        if (dias <= 0) {
            throw new IllegalArgumentException("La fecha de devolución debe ser posterior a la fecha de alquiler (mínimo 1 día)");
        }

        BigDecimal precioTotal = vehiculo.getTarifaDiaria().multiply(BigDecimal.valueOf(dias));
        alquiler.setCliente(cliente);
        alquiler.setVehiculo(vehiculo);
        alquiler.setPrecioTotal(precioTotal);
        alquiler.setEstado("Activo");

        vehiculo.setEstado("Alquilado");
        vehiculoRepository.save(vehiculo);

        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public AlquilerModel devolverVehiculo(Long idAlquiler) {
        AlquilerModel alquiler = alquilerRepository.findById(idAlquiler)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + idAlquiler));

        if ("Completado".equalsIgnoreCase(alquiler.getEstado())) {
            throw new IllegalStateException("El alquiler ya se encuentra completado");
        }

        VehiculoModel vehiculo = alquiler.getVehiculo();
        if (vehiculo != null) {
            vehiculo.setEstado("Disponible");
            vehiculoRepository.save(vehiculo);
        }

        alquiler.setEstado("Completado");
        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public boolean deleteAlquiler(Long id) {
        Optional<AlquilerModel> alquilerOpt = alquilerRepository.findById(id);
        if (alquilerOpt.isPresent()) {
            AlquilerModel alquiler = alquilerOpt.get();
            if (!"Completado".equalsIgnoreCase(alquiler.getEstado())) {
                throw new IllegalStateException("Solo se pueden eliminar contratos que ya están en estado Completado");
            }
            alquilerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
