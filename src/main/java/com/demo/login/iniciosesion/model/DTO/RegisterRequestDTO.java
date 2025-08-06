package com.demo.login.iniciosesion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    /*
     esta clase es para saber que nos tenemos que mandar al usuario
     * */
    private String primerNombre;
    private String apellido;
    private String email;
    private String password;
    private boolean activo;
}