package com.demo.login.iniciosesion.serviceImpl;

import com.demo.login.iniciosesion.model.DTO.AuthResponseDTO;
import com.demo.login.iniciosesion.model.DTO.AuthenticationRequestDTO;
import com.demo.login.iniciosesion.model.DTO.RegisterRequestDTO;
import com.demo.login.iniciosesion.model.Enum.Rol;
import com.demo.login.iniciosesion.model.Usuario;
import com.demo.login.iniciosesion.repository.UsuarioRepository;
import com.demo.login.iniciosesion.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO registrar(RegisterRequestDTO registro) {

        if (null != registro) {

            var usuario = Usuario.builder()
                    .primerNombre(registro.getPrimerNombre())
                    .apellido(registro.getApellido())
                    .email(registro.getEmail())
                    .password(passwordEncoder.encode(registro.getPassword()))
                    .rol(Rol.ADMIN)
                    .activo(true)
                    .build();

            this.usuarioRepository.save(usuario);

            var jwt = this.jwtService.generateToken(usuario);

            return AuthResponseDTO.builder().token(jwt).build();

        } else {

            throw new RuntimeException("Error en la creacion del registro");
        }
    }

    @Override
    public AuthResponseDTO authenticate(AuthenticationRequestDTO registro) {

        // aqui solo autenticamos
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registro.getEmail(), registro.getPassword())
        );

        var usuario = this.usuarioRepository.findUsuarioByEmailAndActivoTrue(
                registro.getEmail()
        ).orElseThrow(() -> new RuntimeException("El email no es correcto"));

        var jwt = this.jwtService.generateToken(usuario);

        return AuthResponseDTO.builder().token(jwt).build();
    }
}
