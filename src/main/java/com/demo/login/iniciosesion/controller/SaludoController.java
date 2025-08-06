package com.demo.login.iniciosesion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saludo")
public class SaludoController {

    @GetMapping("/sayHelloPublic")
    public String saludo() {
        return "saludo desde mi aplicacion";
    }

    @GetMapping("/sayHelloProtegido")
    public String sayHello() {
        return "hello from ajax protegido";
    }


}