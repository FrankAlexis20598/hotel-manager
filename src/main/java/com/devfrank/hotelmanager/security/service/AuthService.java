package com.devfrank.hotelmanager.security.service;

import com.devfrank.hotelmanager.security.dto.request.ForgotPasswordRequest;
import com.devfrank.hotelmanager.security.dto.request.LoginRequest;
import com.devfrank.hotelmanager.security.dto.request.LogoutRequest;
import com.devfrank.hotelmanager.security.dto.request.ResetPasswordRequest;
import com.devfrank.hotelmanager.security.dto.response.TokenResponse;
import com.devfrank.hotelmanager.security.dto.response.UserMeResponse;
import com.devfrank.hotelmanager.security.entity.RefreshToken;
import com.devfrank.hotelmanager.security.jwt.JwtService;
import com.devfrank.hotelmanager.security.repository.RefreshTokenRepository;
import com.devfrank.hotelmanager.shared.constans.ErrorConstants;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import com.devfrank.hotelmanager.users.entity.AppUser;
import com.devfrank.hotelmanager.users.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Transactional
    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetailsImpl userDetailsImpl = Optional.ofNullable(authentication.getPrincipal())
                .map(UserDetailsImpl.class::cast)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, request.email()));
        AppUser user = userDetailsImpl.user();

        String accessToken = jwtService.generateAccessToken(userDetailsImpl);
        String refreshToken = createRefreshToken(user, userDetailsImpl);

        List<String> authorities = userDetailsImpl.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(userDetailsImpl.getUsername())
                .authorities(authorities)
                .build();
    }

    @Transactional
    public TokenResponse refreshToken(String refreshTokenRequest) {
        return refreshTokenRepository.findByToken(refreshTokenRequest)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                    String accessToken = jwtService.generateAccessToken(userDetails);

                    refreshTokenRepository.deleteByToken(refreshTokenRequest);
                    String newRefreshToken = createRefreshToken(user, userDetails);

                    List<String> authorities = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList();

                    return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(newRefreshToken)
                            .email(user.getEmail())
                            .authorities(authorities)
                            .build();
                })
                .orElseThrow(() -> new BusinessException(ErrorConstants.AUTH_INVALID_REFRESH_TOKEN));
    }

    @Transactional
    public void logout(LogoutRequest request) {
        refreshTokenRepository.deleteByToken(request.refreshToken());
    }

    public UserMeResponse getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetailsImpl = Optional.ofNullable(authentication.getPrincipal())
                .map(UserDetailsImpl.class::cast)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, authentication.getName()));

        AppUser user = userDetailsImpl.user();

        Set<String> permissions = user.getRole().getPermissions().stream()
                .map(p -> p.getModule() + ":" + p.getAction())
                .collect(Collectors.toSet());

        return UserMeResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().getName())
                .permissions(permissions)
                .build();
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.USER, request.email()));

        user.setResetPasswordToken(UUID.randomUUID().toString());
        user.setResetPasswordExpiresAt(LocalDateTime.now().plusMinutes(30));
        appUserRepository.save(user);

        // TODO Enviar correo al usuario con el password token
        System.out.println("Reset token for " + user.getEmail() + ": " + user.getResetPasswordToken());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        AppUser user = appUserRepository.findByResetPasswordToken(request.token())
                .orElseThrow(() -> new BusinessException(ErrorConstants.AUTH_INVALID_PASSWORD_TOKEN));

        if (user.getResetPasswordExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ErrorConstants.AUTH_EXPIRED_PASSWORD_TOKEN);
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiresAt(null);
        appUserRepository.save(user);
    }

    private String createRefreshToken(AppUser user, UserDetails userDetails) {
        refreshTokenRepository.deleteByUser(user);

        var now = Instant.now();

        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .user(user)
                .token(jwtService.generateRefreshToken(userDetails.getUsername(), Date.from(now)))
                .expiryDate(now.plusMillis(refreshTokenExpiration))
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new BusinessException(ErrorConstants.AUTH_EXPIRED_REFRESH_TOKEN);
        }
        return token;
    }
}
