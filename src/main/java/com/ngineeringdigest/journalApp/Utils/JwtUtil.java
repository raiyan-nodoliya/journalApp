package com.ngineeringdigest.journalApp.Utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	

	    public static final long JWT_TOKEN_VALIDITY_SECONDS = 1 * 60 ; // 5 minutes

	    // ✅ secret should be 64+ chars for HS512; yours is long enough
	    private final String SECRET_KEY =
	            "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	    private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	    }

	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    private boolean isTokenExpired(String token) {
	        Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	    public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, username);
	    }

	    private String createToken(Map<String, Object> claims, String subject) {
	        long now = System.currentTimeMillis();

	        return Jwts.builder()
	                .setHeaderParam("typ", "JWT")
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(now))
	                .setExpiration(new Date(now + JWT_TOKEN_VALIDITY_SECONDS * 1000))
	                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
	                .compact();
	    }

	    public boolean validateToken(String token, UserDetails userDetails) {
	        String username = getUsernameFromToken(token);
	        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	    }

		
	

}
