package com.demo.login.iniciosesion.serviceImpl;

import com.demo.login.iniciosesion.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expirationMs}")
    private Long jwtExpirationMs;


    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }
    private String generateToken(Map<String, Objects> extraClaim , UserDetails userDetails) {

        return Jwts.builder().setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getUsername(String jwt) {

        return getClaim(jwt, Claims::getSubject);
    }
    private <T> T getClaim(String token, Function<Claims,T> claimsResult) {

        final Claims claims = getAllClaims(token);
        return claimsResult.apply(claims);
    }

    private Claims getAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }


    @Override
    public  boolean valideToken(String jwt, UserDetails userDetails) {

        final String username = this.getUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpire(jwt);
    }

    private boolean isTokenExpire(String jwt) {

        return this.getExpiration(jwt).before(new Date());
    }

    private Date getExpiration(String jwt) {
        return this.getClaim(jwt, Claims::getExpiration);
    }

}
