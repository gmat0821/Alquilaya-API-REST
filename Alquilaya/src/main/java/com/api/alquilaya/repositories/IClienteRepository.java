package com.api.alquilaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.api.alquilaya.models.ClienteModel;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteModel, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si es necesario, por ejemplo:
    Optional<ClienteModel> findByEmail(String email);
    Optional<ClienteModel> findByLicenciaConducir(String licenciaConducir);
}
