package com.hackathon.bespring.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon.bespring.domain.category.domain.CategoryStatus;
import com.hackathon.bespring.domain.category.domain.repository.CategoryRepository;
import com.hackathon.bespring.domain.category.domain.repository.CategoryStatusRepository;
import com.hackathon.bespring.domain.category.exception.CategoryNotFound;
import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.domain.user.domain.repository.UserRepository;
import com.hackathon.bespring.domain.user.exception.InvalidPassword;
import com.hackathon.bespring.domain.user.exception.UserExists;
import com.hackathon.bespring.domain.user.presentation.dto.request.GetUserRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.LocationRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignInRequest;
import com.hackathon.bespring.domain.user.presentation.dto.request.SignUpRequest;
import com.hackathon.bespring.domain.user.presentation.dto.response.PhoneNumberResponse;
import com.hackathon.bespring.domain.user.presentation.dto.response.TokenResponse;
import com.hackathon.bespring.domain.webpush.domain.WebPush;
import com.hackathon.bespring.domain.webpush.domain.repository.WebPushRepository;
import com.hackathon.bespring.domain.webpush.presentation.dto.request.WebPushSendDto;
import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;
import com.hackathon.bespring.global.security.exception.UserNotFound;
import com.hackathon.bespring.global.security.jwt.JwtTokenProvider;
import com.hackathon.bespring.global.util.UserUtil;
import com.hackathon.bespring.global.util.WebPushUtil;
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
    private final WebPushRepository webPushRepository;
    private final WebPushUtil webPushUtil;
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

    public PhoneNumberResponse getUser(GetUserRequest request) {
        List<User> userList = userRepository.findAllUser(request.getLatitude(), request.getLongitude());
        List<WebPush> webPushList = webPushRepository.findAllByUserIn(userList);
        WebPushSendDto webPushSendDto = WebPushSendDto.builder()
                .title("도움이 필요한 사람이 발생하였습니다")
                .body("알림을 클릭하여 도와주기")
                .link("http://localhost:3000/accept?phoneNumber=" + request.getPhoneNumber())
                .build();

        try {
            webPushUtil.sendNotificationToAll(webPushList, webPushSendDto);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return new PhoneNumberResponse(request.getPhoneNumber());
    }

    @Transactional
    public void updateLocation(LocationRequest request) {
        User user = userUtil.getCurrentUser();
        user.updateLocation(request.getLatitude(), request.getLongitude());
    }

    private TokenResponse getToken(String accountId) {
        String accessToken = jwtTokenProvider.generateToken(accountId);
        return new TokenResponse(accessToken);
    }
}
