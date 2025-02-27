package ir.setad.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.JwkThumbprint;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.expireDate}")
    private Long expireDate;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return buildToken(claims,userDetails,expireDate);
    }


    public String buildToken(
            Map<String, Object> claims,
            UserDetails userDetails,
            Long expireIn) {

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireIn))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isExpire(String token){
        Claims claims=parseToken(token);
       Date expiration= claims.getExpiration();
       return expiration.before(new Date());
    }

    private Key getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractToken(String token) {
        Claims claims=parseToken(token);
        return claims.getSubject();
    }
    private Claims parseToken(String token) {
       return Jwts.parser().setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
