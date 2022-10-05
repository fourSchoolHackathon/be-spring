package com.hackathon.bespring.domain.user.service;

import com.hackathon.bespring.domain.user.domain.Role;
import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.domain.user.domain.repository.UserRepository;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignInRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignUpRequest;
import com.hackathon.bespring.domain.user.presentation.dto.response.TokenResponse;
import com.hackathon.bespring.domain.user.presentation.exception.InvalidPassword;
import com.hackathon.bespring.domain.user.presentation.exception.UserExists;
import com.hackathon.bespring.global.security.exception.UserNotFound;
import com.hackathon.bespring.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse signUp(SignUpRequest request) {
        if (userRepository.existsByAccountId(request.getAccountId())) {
            throw UserExists.EXCEPTION;
        }

        User user = User.builder()
                .accountId(request.getAccountId())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return getToken(user.getAccountId());
    }

    public TokenResponse signIn(SignInRequest request) {
        User user = userRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> UserNotFound.EXCEPTION);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw InvalidPassword.EXCEPTION;
        }

        return getToken(user.getAccountId());
    }

    private TokenResponse getToken(String accountId) {
        String accessToken = jwtTokenProvider.generateToken(accountId);
        return new TokenResponse(accessToken);
    }
}
