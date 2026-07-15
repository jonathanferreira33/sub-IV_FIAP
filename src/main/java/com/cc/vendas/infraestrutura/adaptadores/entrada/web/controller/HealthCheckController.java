package com.cc.vendas.infraestrutura.adaptadores.entrada.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HealthCheckController {

    @GetMapping
    public String test() {
        return "ok vehicle-service";
    }
}
