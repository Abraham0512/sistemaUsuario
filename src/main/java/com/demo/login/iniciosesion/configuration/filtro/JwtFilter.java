package com.demo.login.iniciosesion.configuration.filtro;

import com.demo.login.iniciosesion.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    /***
     *  request = es la solicitud que envía el cliente
     *  response = es la respuesta una vez procesada la información
     *  filterChain = elemento que nos permite continuar con el proceso de la solicitud
     *               una vez filtrado
     * */

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (null == authHeader || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // obtener desde la posicion 7
        jwt = authHeader.substring(7);
        // crea el servicio de jwt, para obtener los valores del token por separados
        userEmail = jwtService.getUsername(jwt);

        // validamos que no sea null y que no este logeado
        if (null != userEmail && null == SecurityContextHolder.getContext().getAuthentication()) {

            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(userEmail);

            // validar el token
            if (jwtService.valideToken(jwt, userDetails)) {

                // creamos el token
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, "", userDetails.getAuthorities()
                        );

                // creamos el detalle dentro de la petición
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // actualizamos el security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}