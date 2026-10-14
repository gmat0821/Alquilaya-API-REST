package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.api.alquilaya.models.OficinaAlquilerModel;
import com.api.alquilaya.repositories.IOficinaAlquilerRepository;

@Service
public class OficinaAlquilerService {

    private final IOficinaAlquilerRepository oficinaRepository;

    public OficinaAlquilerService(IOficinaAlquilerRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    public List<OficinaAlquilerModel> getAllOficinas() {
        return oficinaRepository.findAll();
    }

    public Optional<OficinaAlquilerModel> getOficinaById(Long id) {
        return oficinaRepository.findById(id);
    }

    public List<OficinaAlquilerModel> getOficinasPorCiudad(String ciudad) {
        return oficinaRepository.findByCiudadIgnoreCase(ciudad);
    }

    public OficinaAlquilerModel saveOficina(OficinaAlquilerModel oficina) {
        return oficinaRepository.save(oficina);
    }

    public Optional<OficinaAlquilerModel> updateOficina(Long id, OficinaAlquilerModel oficinaActualizada) {
        return oficinaRepository.findById(id).map(oficina -> {
            oficina.setNombre(oficinaActualizada.getNombre());
            oficina.setDireccion(oficinaActualizada.getDireccion());
            oficina.setCiudad(oficinaActualizada.getCiudad());
            oficina.setRegion(oficinaActualizada.getRegion());
            return oficinaRepository.save(oficina);
        });
    }

    public boolean deleteOficina(Long id) {
        if (oficinaRepository.existsById(id)) {
            oficinaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
