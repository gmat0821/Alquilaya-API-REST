package com.api.alquilaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.api.alquilaya.models.AlquilerModel;

@Repository
public interface IAlquilerRepository extends JpaRepository<AlquilerModel, Long> {
    List<AlquilerModel> findByEstadoIgnoreCase(String estado);
    List<AlquilerModel> findByCliente_IdCliente(Long idCliente);
    List<AlquilerModel> findByVehiculo_IdVehiculo(Long idVehiculo);
}
