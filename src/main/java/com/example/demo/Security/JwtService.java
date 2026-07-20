package com.example.demo.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey12345";
       private Key getsignKey(){
           return Keys.hmacShaKeyFor(SECRET.getBytes());
       }



    private String generateJwtToken(String email, long expirationTime) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getsignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
       public String generateToken(String email){
           return generateJwtToken(email,1000*60*15);

       }
    public String generateRefreshToken(String email) {
        return generateJwtToken(email, 1000L * 60 * 60 * 24 * 7);
    }
    public String validateRefreshToken(String refreshToken) {

        String email = extractUsername(refreshToken);

        if (!isTokenValid(refreshToken, email)) {
            throw new RuntimeException("Invalid refresh token");
        }

        return email;
    }
     public String  extractUsername(String token){
           return Jwts.parser()
                   .setSigningKey(getsignKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();


     }
     public boolean isTokenValid(String token,String email){
           String username=extractUsername(token);
           return username.equals(email)&&!isTokenExpired(token);

     }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getsignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

}
