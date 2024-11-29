package PetrovTodor.PepeMedicalKids.security;


import PetrovTodor.PepeMedicalKids.entities.users.User;
import PetrovTodor.PepeMedicalKids.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;


    public String createToken(User utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) //<-- vanno messi in millisecondi
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) //<-- scadenza del token sempre in millisecondi
                .subject(String.valueOf(utente.getIdUtente()))//<-- a chi appartiene il token (NON METTERE DATI SENSIBILI!)
                .claim("ruolo", utente.getRuolo().name()) // Aggiungi il ruolo come claim
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))// <-- con questo firmo il token
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi con il token, per favore effettua di nuovo il login!");
        }
    }

    public String extractDipendenteFromToken(String accessToken) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(accessToken)
                .getPayload().getSubject();
    }

    public String extractRuoloFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseClaimsJws(token)
                .getBody();
        String ruolo = claims.get("ruolo", String.class);

        return ruolo;
    }
}
