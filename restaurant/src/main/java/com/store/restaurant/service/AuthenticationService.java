package com.store.restaurant.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.store.restaurant.dto.request.requestService.AuthenticationRequest;
import com.store.restaurant.dto.request.requestService.IntrospectRequest;
import com.store.restaurant.dto.request.requestService.LogoutRequest;
import com.store.restaurant.dto.request.requestService.RefreshRequest;
import com.store.restaurant.dto.response.responseService.AuthenticationResponse;
import com.store.restaurant.dto.response.responseService.IntrospectResponse;
import com.store.restaurant.entity.InvalidToken;
import com.store.restaurant.entity.User;
import com.store.restaurant.exception.AppException;
import com.store.restaurant.exception.ErrorCode;
import com.store.restaurant.repository.InvalidTokenRepository;
import com.store.restaurant.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long validDuration;

    @NonFinal
    @Value("${jwt.refresh-duration}")
    protected Long refreshDuration;

    public IntrospectResponse introspectResponse(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isvalid = true;
        try{
            verifyToken(token, false);
        }catch (AppException e){
            isvalid = false;
        }
        return IntrospectResponse.builder()
                .valid(isvalid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        log.info("SignerKey: {}", signerKey);
        var user = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(), true);
        var jid = signJWT.getJWTClaimsSet().getJWTID();
        var expityTime = signJWT.getJWTClaimsSet().getExpirationTime();
        InvalidToken invalidToken = InvalidToken.builder()
                .id(jid)
                .expityTime(expityTime)
                .build();

        invalidTokenRepository.save(invalidToken);

        var phoneNumber = signJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException{
        try {
            var signToken = verifyToken(request.getToken(), true);
            var jid = signToken.getJWTClaimsSet().getJWTID();
            Date expityTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidToken invalidToken = InvalidToken.builder()
                    .id(jid)
                    .expityTime(expityTime)
                    .build();
            invalidTokenRepository.save(invalidToken);
        }catch (AppException e){
            log.info("token is invalid");
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }


    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getPhoneNumber())
                .issuer("restaurant")
                .issueTime(new Date())
                .expirationTime(Date.from(
                        Instant.now().plusMillis(validDuration)))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("userId", user.getId())
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try{
            jwsObject.sign(new MACSigner(signerKey.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("cannot crate token", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException{
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes(StandardCharsets.UTF_8));
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date now = new Date();
        Date expityTime;
        if(isRefresh){
            Date issuTime = signedJWT.getJWTClaimsSet().getIssueTime();
            expityTime = Date.from(issuTime.toInstant().plusMillis(refreshDuration));
        }else {
            expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        }

        boolean verifired = signedJWT.verify(verifier);
        if(!(verifired && expityTime.after(now))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if(invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        Optional.ofNullable(user.getRole()).ifPresent(role -> stringJoiner.add("ROLE_" + role.getName()));
        return stringJoiner.toString();
    }
}
