package com.api.alquilaya.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.api.alquilaya.models.ClienteModel;
import com.api.alquilaya.repositories.IClienteRepository;

@Service 
public class ClienteService {

    private final IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteModel> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public ClienteModel saveCliente(ClienteModel cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<ClienteModel> getClienteByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Optional<ClienteModel> getClienteByLicencia(String licencia) {
        return clienteRepository.findByLicenciaConducir(licencia);
    }

    public Optional<ClienteModel> updateCliente(Long id, ClienteModel clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setLicenciaConducir(clienteActualizado.getLicenciaConducir());
            cliente.setTelefono(clienteActualizado.getTelefono());
            return clienteRepository.save(cliente);
        });
    }

    public boolean deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
