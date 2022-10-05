package com.hackathon.bespring.domain.user.service;

import com.hackathon.bespring.domain.category.domain.Category;
import com.hackathon.bespring.domain.category.domain.repository.CategoryRepository;
import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.domain.user.domain.repository.UserRepository;
import com.hackathon.bespring.domain.user.exception.InvalidPassword;
import com.hackathon.bespring.domain.user.exception.UserExists;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignInRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignUpRequest;
import com.hackathon.bespring.domain.user.presentation.dto.response.TokenResponse;
import com.hackathon.bespring.global.security.exception.UserNotFound;
import com.hackathon.bespring.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryRepository categoryRepository;

    public TokenResponse signUp(SignUpRequest request) {
        if (userRepository.existsByAccountId(request.getAccountId())) {
            throw UserExists.EXCEPTION;
        }

        User user = User.builder()
                .accountId(request.getAccountId())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .sex(request.getSex())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        userRepository.save(user);

        List<Category> categoryList = request.getCategories()
                .stream()
                .map(categories -> Category.builder()
                        .category(categories)
                        .user(user)
                        .build()).toList();
        categoryRepository.saveAll(categoryList);

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
