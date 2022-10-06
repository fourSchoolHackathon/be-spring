package com.hackathon.bespring.domain.user.service;

import com.hackathon.bespring.domain.application.domain.Application;
import com.hackathon.bespring.domain.application.domain.repository.ApplicationRepository;
import com.hackathon.bespring.domain.application.presentation.dto.response.ApplicationHistoryResponse;
import com.hackathon.bespring.domain.category.domain.CategoryStatus;
import com.hackathon.bespring.domain.category.domain.repository.CategoryRepository;
import com.hackathon.bespring.domain.category.domain.repository.CategoryStatusRepository;
import com.hackathon.bespring.domain.category.exception.CategoryNotFound;
import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.domain.user.domain.repository.UserRepository;
import com.hackathon.bespring.domain.user.exception.InvalidPassword;
import com.hackathon.bespring.domain.user.exception.UserExists;
import com.hackathon.bespring.domain.user.presentation.dto.request.LocationRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignInRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignUpRequest;
import com.hackathon.bespring.domain.user.presentation.dto.response.ApplicationHistoryElement;
import com.hackathon.bespring.domain.user.presentation.dto.response.TokenResponse;
import com.hackathon.bespring.global.security.exception.UserNotFound;
import com.hackathon.bespring.global.security.jwt.JwtTokenProvider;
import com.hackathon.bespring.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryRepository categoryRepository;
    private final CategoryStatusRepository categoryStatusRepository;
    private final ApplicationRepository applicationRepository;
    private final UserUtil userUtil;

    @Transactional
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

        List<CategoryStatus> categoryList = request.getCategories()
                .stream()
                .map(categories -> categoryRepository.findByCategory(categories)
                        .orElseThrow(() -> CategoryNotFound.EXCEPTION))
                .map(category -> CategoryStatus.builder()
                        .category(category)
                        .user(user)
                        .build()).toList();
        categoryStatusRepository.saveAll(categoryList);

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

    @Transactional
    public void updateLocation(LocationRequest request) {
        User user = userUtil.getCurrentUser();
        user.updateLocation(request.getLatitude(), request.getLongitude());
    }

    public ApplicationHistoryResponse callHistoryListApplication() {
        User user = userUtil.getCurrentUser();
        List<Application> applicationList = applicationRepository.findAllByUserOrderByCreatedAtDesc(user);

        List<ApplicationHistoryElement> elements = applicationList
                .stream()
                .map(application -> new ApplicationHistoryElement(application.getPhoneNumber(), application.getCreatedAt()))
                .toList();

        return new ApplicationHistoryResponse(user.getName(), elements);
    }

    private TokenResponse getToken(String accountId) {
        String accessToken = jwtTokenProvider.generateToken(accountId);
        return new TokenResponse(accessToken);
    }
}
