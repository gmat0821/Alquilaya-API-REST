package com.api.alquilaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.api.alquilaya.models.OficinaAlquilerModel;

@Repository
public interface IOficinaAlquilerRepository extends JpaRepository<OficinaAlquilerModel, Long> {
    List<OficinaAlquilerModel> findByCiudadIgnoreCase(String ciudad);
}
