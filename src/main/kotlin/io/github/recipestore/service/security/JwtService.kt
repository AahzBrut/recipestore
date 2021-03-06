package io.github.recipestore.service.security

import io.github.recipestore.domain.Role
import io.github.recipestore.domain.User
import io.github.recipestore.util.ROLE_CLAIMS
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date
import kotlin.collections.set


@Service
class JwtService {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private lateinit var expirationTime: String

    fun extractUsername(authToken: String): String? {
        return getClaimsFromToken(authToken)
            .subject
    }

    fun getClaimsFromToken(authToken: String): Claims {
        val key: String = Base64.getEncoder().encodeToString(secret.toByteArray())
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(authToken)
            .body
    }

    @Suppress("UNCHECKED_CAST")
    fun getAuthoritiesFromToken(authToken: String): List<GrantedAuthority> {
        val claims = getClaimsFromToken(authToken)
        val roles = claims[ROLE_CLAIMS] as List<String>
        return roles
            .map{ "ROLE_$it"}
            .map(::SimpleGrantedAuthority)
            .toList()
    }

    fun validateToken(authToken: String): Boolean {
        return getClaimsFromToken(authToken)
            .expiration
            .after(Date())
    }

    fun generateToken(user: User): String {
        val claims = HashMap<String, Any?>()
        claims[ROLE_CLAIMS] = user.roles.map(Role::name).toList()
        val creationDate = Date()
        val expirationDate = Date(creationDate.time + expirationTime.toLong())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.name)
            .setIssuedAt(creationDate)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .compact()
    }
}