package org.chukwuma.validation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.Value;

import java.util.Date;
import java.util.function.Function;

public class JWTClass {

    private static final String secretKey = "dOx1l87J#i#HfCf7pdZAk&C^08WuT$dzZvdqFxLlpeZ3pv*w%tYqktDb!nUf!oY3";


    public static String generateJwtToken(String username, String email) {
//        long expirationTime = 86400000; // 1 day in milliseconds
        long expirationTime = 1800000; // 1 day in milliseconds

        return Jwts.builder()
                .setSubject(username)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public static Claims verifyJwtToken(String token) {
//
//        try {
//            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//        } catch (SignatureException e) {
//            // Invalid token or signature
//            return null;
//        }
//    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Boolean validateToken(String token, String username) {
        final String user = extractUsername(token);
        return (user.equals(username) && !isTokenExpired(token));
    }

}
