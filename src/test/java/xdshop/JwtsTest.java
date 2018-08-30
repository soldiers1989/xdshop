package xdshop;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtsTest {

	public static void main(String[] args) {
		String token=Jwts.builder().setSubject("admin")
	            .claim("roles", "role1").setIssuedAt(new Date())
	            .signWith(SignatureAlgorithm.HS256, "xdshop_pkey").compact();
		System.out.println("token:"+token);
		
		Claims claims = Jwts.parser().setSigningKey("xdshop_pkey").parseClaimsJws(token).getBody();
        
		String userId=claims.getSubject();
		System.out.println(userId);
	}

}
