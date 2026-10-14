package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.alquilaya.models.VehiculoModel;
import com.api.alquilaya.repositories.IOficinaAlquilerRepository;
import com.api.alquilaya.repositories.IVehiculoRepository;

@Service
public class VehiculoService {

    private final IVehiculoRepository vehiculoRepository;
    private final IOficinaAlquilerRepository oficinaRepository;

    public VehiculoService(IVehiculoRepository vehiculoRepository, IOficinaAlquilerRepository oficinaRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.oficinaRepository = oficinaRepository;
    }

    public List<VehiculoModel> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Optional<VehiculoModel> getVehiculoById(Long id) {
        return vehiculoRepository.findById(id);
    }

    public List<VehiculoModel> getVehiculosPorEstado(String estado) {
        return vehiculoRepository.findByEstadoIgnoreCase(estado);
    }

    @Transactional
    public VehiculoModel saveVehiculo(VehiculoModel vehiculo) {
        if (vehiculo.getEstado() == null || vehiculo.getEstado().isBlank()) {
            vehiculo.setEstado("Disponible");
        }
        if (vehiculo.getOficina() != null && vehiculo.getOficina().getIdOficina() != null) {
            oficinaRepository.findById(vehiculo.getOficina().getIdOficina())
                    .ifPresent(vehiculo::setOficina);
        }
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public Optional<VehiculoModel> updateVehiculo(Long id, VehiculoModel vehiculoActualizado) {
        return vehiculoRepository.findById(id).map(vehiculo -> {
            vehiculo.setMarca(vehiculoActualizado.getMarca());
            vehiculo.setModelo(vehiculoActualizado.getModelo());
            vehiculo.setAnio(vehiculoActualizado.getAnio());
            vehiculo.setMatricula(vehiculoActualizado.getMatricula());
            vehiculo.setTarifaDiaria(vehiculoActualizado.getTarifaDiaria());
            if (vehiculoActualizado.getEstado() != null) {
                vehiculo.setEstado(vehiculoActualizado.getEstado());
            }
            if (vehiculoActualizado.getOficina() != null && vehiculoActualizado.getOficina().getIdOficina() != null) {
                oficinaRepository.findById(vehiculoActualizado.getOficina().getIdOficina())
                        .ifPresent(vehiculo::setOficina);
            }
            return vehiculoRepository.save(vehiculo);
        });
    }

    @Transactional
    public boolean actualizarEstado(Long idVehiculo, String nuevoEstado) {
        Optional<VehiculoModel> vehiculoOpt = vehiculoRepository.findById(idVehiculo);
        if (vehiculoOpt.isPresent()) {
            VehiculoModel vehiculo = vehiculoOpt.get();
            vehiculo.setEstado(nuevoEstado);
            vehiculoRepository.save(vehiculo);
            return true;
        }
        return false;
    }

    public boolean deleteVehiculo(Long id) {
        if (vehiculoRepository.existsById(id)) {
            vehiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
