package com.demo.login.iniciosesion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {

/*esta clase es la que funciona para el login , es donde el usuario
* envia su correo y password
* */

    private String email;
    private String password;
}
