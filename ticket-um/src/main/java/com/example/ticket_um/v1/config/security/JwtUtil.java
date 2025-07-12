package com.example.ticket_um.v1.config.security;

import com.example.ticket_um.v1.domain.model.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import static com.example.ticket_um.v1.domain.model.enums.TokenType.OTP;
import static com.example.ticket_um.v1.domain.model.enums.TokenType.SESSION;

@Slf4j
@Component
public class JwtUtil {

  private final String privateKeyStr;
  private final String publicKeyStr;
  private final long expAuth;
  private final long expOtp;

  public JwtUtil(
      @Value("${jwt.private.key}") String privateKeyStr,
      @Value("${jwt.public.key}") String publicKeyStr,
      @Value("${jwt.auth.exp}") long expAuth,
      @Value("${jwt.otp.exp}") long expOtp) {
    this.privateKeyStr = privateKeyStr
        .replace("\n", "")
        .replace("-----BEGIN PRIVATE KEY-----", "")
        .replace("-----END PRIVATE KEY-----", "");
    this.publicKeyStr = publicKeyStr
        .replace("\n", "")
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "");
    this.expAuth = expAuth;
    this.expOtp = expOtp;
  }

  public String generateJwt(Long userId, TokenType tokenType) {
    var exp = tokenType == OTP ? expOtp : expAuth;
    exp += System.currentTimeMillis();
    var jwtBuilder = Jwts.builder()
        .id(UUID.randomUUID().toString())
        .issuedAt(new Date())
        .claim("userId", userId.toString())
        .claim("tokenType", tokenType)
        .signWith(getPrivateKey());

    if (tokenType != SESSION) {
      jwtBuilder.expiration(new Date(exp));
    }

    return jwtBuilder.compact();
  }

  public Claims validate(String token) {
    return Jwts.parser()
        .verifyWith(getPublicKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private PrivateKey getPrivateKey() {
    var privateKeyEncoded = Base64.getDecoder().decode(privateKeyStr);
    var keySpec = new PKCS8EncodedKeySpec(privateKeyEncoded);

    PrivateKey privateKey;
    try {
      privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      log.error("LoadPrivateKey", ex);
      throw new IllegalStateException(ex.getMessage());
    }
    return privateKey;
  }

  private PublicKey getPublicKey() {
    byte[] puplicKeyEncoded = Base64.getDecoder().decode(publicKeyStr);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(puplicKeyEncoded);

    PublicKey publicKey;
    try {
      publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      log.error("LoadPublicKey", ex);
      throw new IllegalStateException(ex.getMessage());
    }
    return publicKey;
  }
}
