package com.demo.login.iniciosesion.service;

import com.demo.login.iniciosesion.model.DTO.AuthResponseDTO;
import com.demo.login.iniciosesion.model.DTO.AuthenticationRequestDTO;
import com.demo.login.iniciosesion.model.DTO.RegisterRequestDTO;

public interface UsuarioService {
    AuthResponseDTO registrar(RegisterRequestDTO registro);
    AuthResponseDTO authenticate(AuthenticationRequestDTO registro);
}
