package com.api.alquilaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.api.alquilaya.models.VehiculoModel;

@Repository
public interface IVehiculoRepository extends JpaRepository<VehiculoModel, Long> {
    Optional<VehiculoModel> findByMatricula(String matricula);
    List<VehiculoModel> findByEstadoIgnoreCase(String estado);
    List<VehiculoModel> findByOficina_IdOficina(Long idOficina);
}
