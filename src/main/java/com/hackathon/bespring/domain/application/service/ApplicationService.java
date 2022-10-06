package com.hackathon.bespring.domain.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon.bespring.domain.application.presentation.dto.request.DetailsApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.request.UrgentApplicationRequest;
import com.hackathon.bespring.domain.application.presentation.dto.response.DetailsApplicationResponse;
import com.hackathon.bespring.domain.application.presentation.dto.response.PhoneNumberResponse;
import com.hackathon.bespring.domain.category.domain.Category;
import com.hackathon.bespring.domain.category.domain.repository.CategoryRepository;
import com.hackathon.bespring.domain.category.exception.CategoryNotFound;
import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.domain.user.domain.repository.UserRepository;
import com.hackathon.bespring.domain.webpush.domain.WebPush;
import com.hackathon.bespring.domain.webpush.domain.repository.WebPushRepository;
import com.hackathon.bespring.domain.webpush.presentation.dto.request.WebPushSendDto;
import com.hackathon.bespring.global.error.CustomException;
import com.hackathon.bespring.global.error.ErrorCode;
import com.hackathon.bespring.global.util.WebPushUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final UserRepository userRepository;
    private final WebPushRepository webPushRepository;
    private final WebPushUtil webPushUtil;
    private final CategoryRepository categoryRepository;

    public PhoneNumberResponse urgentApplication(UrgentApplicationRequest request) {
        List<User> userList = userRepository.findAllUser(request.getLatitude(), request.getLongitude());

        WebPushSendDto webPushSendDto = WebPushSendDto.builder()
                .title("빠른 도움이 필요한 사람이 발생하였습니다")
                .body("알림을 클릭하여 바로 전화걸기")
                .link("tel:" + request.getPhoneNumber())
                .build();
        webPush(userList, webPushSendDto);

        return new PhoneNumberResponse(request.getPhoneNumber());
    }

    public DetailsApplicationResponse detailsApplication(DetailsApplicationRequest request) {
        List<Category> categoryList = request.getCategoryList()
                .stream()
                .map(categories -> categoryRepository.findByCategory(categories)
                        .orElseThrow(() -> CategoryNotFound.EXCEPTION)
                ).toList();

        List<User> userList = userRepository.findAllUserByCategory(
                request.getLatitude(), request.getLongitude(),
                categoryList.get(0).getId(), categoryList.get(1).getId()
        );

        WebPushSendDto webPushSendDto = WebPushSendDto.builder()
                .title("도움이 필요한 사람이 발생하였습니다")
                .body("알림을 클릭하여 도와주기")
                .link("http://localhost:3000/accept?phoneNumber=" + request.getPhoneNumber())
                .build();
        webPush(userList, webPushSendDto);

        return DetailsApplicationResponse.builder()
                .phoneNumber(request.getPhoneNumber())
                .sex(request.getSex())
                .categoryList(request.getCategoryList())
                .startAt(request.getStartAt())
                .endAt(request.getEndAt())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .build();
    }

    private void webPush(List<User> userList, WebPushSendDto webPushSendDto) {
        List<WebPush> webPushList = webPushRepository.findAllByUserIn(userList);
        try {
            webPushUtil.sendNotificationToAll(webPushList, webPushSendDto);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
