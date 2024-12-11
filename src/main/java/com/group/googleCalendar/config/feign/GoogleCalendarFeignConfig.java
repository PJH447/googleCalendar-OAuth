package com.group.googleCalendar.config.feign;

import com.group.googleCalendar.web.googleCalendar.dto.GoogleAccessTokenReq;
import com.group.googleCalendar.web.googleCalendar.dto.GoogleAccessTokenRes;
import com.group.googleCalendar.web.googleCalendar.service.GoogleOAuthTokenService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleCalendarFeignConfig implements RequestInterceptor {

    private static String AUTHORIZATION_HEADER = null;
    private static Instant AUTH_HEADER_EXPIRED_TIME = Instant.now();

    @Value("${google.calendar.iss}")
    private String iss;

    @Value("${google.calendar.private-key}")
    private String privateKeyFromJson;

    @Value("${google.calendar.private-key-id}")
    private String privateKeyIdFromJson;

    @Value("${google.calendar.expiration-time}")
    private long expirationInterval;

    private final GoogleOAuthTokenService googleOAuthTokenService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (AUTHORIZATION_HEADER == null || AUTH_HEADER_EXPIRED_TIME.isBefore(Instant.now())) {
            GoogleAccessTokenRes accessTokenRes = null;
            try {
                accessTokenRes = this.getAccessToken();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            AUTHORIZATION_HEADER = "Bearer " + accessTokenRes.access_token();
            AUTH_HEADER_EXPIRED_TIME = Instant.now().plus(accessTokenRes.expires_in(), ChronoUnit.SECONDS);
        }

        requestTemplate.header("Authorization", AUTHORIZATION_HEADER);
        requestTemplate.header("X-GFE-SSL", "yes");
    }

    @Bean
    public FeignClientExceptionErrorDecoder commonFeignErrorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    private GoogleAccessTokenRes getAccessToken() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String googleJwtToken = this.getGoogleJwtToken();
        GoogleAccessTokenReq request = GoogleAccessTokenReq.builder()
                                                           .grant_type("urn:ietf:params:oauth:grant-type:jwt-bearer")
                                                           .assertion(googleJwtToken)
                                                           .build();
        GoogleAccessTokenRes googleAccessToken = googleOAuthTokenService.getGoogleAccessToken(request);
        return googleAccessToken;
    }

    private String getGoogleJwtToken() throws NoSuchAlgorithmException, InvalidKeySpecException {

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", iss);
        claims.put("scope", "https://www.googleapis.com/auth/calendar.readonly https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/calendar.events https://www.googleapis.com/auth/calendar.events.readonly");
        claims.put("aud", "https://oauth2.googleapis.com/token");

        String privateKeyPEM = privateKeyFromJson
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\\\n", "")
                .replaceAll("\\n", "")
                .replaceAll("\\s+", "")
                .trim();

        byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKeyPEM);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(keySpec);

        Map<String, Object> headers = new HashMap<>();
        headers.put("kid", privateKeyIdFromJson);

        return Jwts.builder()
                   .setClaims(claims)
                   .setHeader(headers)
                   .signWith(SignatureAlgorithm.RS256, privateKey)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + expirationInterval))
                   .compact();
    }

}