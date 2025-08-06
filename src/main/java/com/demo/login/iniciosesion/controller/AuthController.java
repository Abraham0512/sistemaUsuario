package com.demo.login.iniciosesion.controller;

import com.demo.login.iniciosesion.model.DTO.AuthResponseDTO;
import com.demo.login.iniciosesion.model.DTO.AuthenticationRequestDTO;
import com.demo.login.iniciosesion.model.DTO.RegisterRequestDTO;
import com.demo.login.iniciosesion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO>crearRegistro(@RequestBody RegisterRequestDTO registro) {
        return ResponseEntity.ok(usuarioService.registrar(registro));
    }

    @PostMapping("/autenticar")
    public ResponseEntity<AuthResponseDTO> crearRegistro(@RequestBody AuthenticationRequestDTO registro) {

        return ResponseEntity.ok(usuarioService.authenticate(registro));
    }

}