package com.api.alquilaya.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/")
    public String inicio() {return "index";}

    @GetMapping({"/clientes", "/clientes-vista"})
    public String clientes() {
        return "clientes";
    }

    @GetMapping({"/vehiculos", "/vehiculos-vista"})
    public String vehiculos() {
        return "vehiculos";
    }

    @GetMapping({"/alquileres", "/alquileres-vista"})
    public String alquileres() {
        return "alquileres";
    }

    @GetMapping({"/mantenimientos", "/mantenimientos-vista"})
    public String mantenimientos() {
        return "mantenimientos";
    }
}
