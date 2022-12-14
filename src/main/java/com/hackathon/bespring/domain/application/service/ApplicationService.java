package com.hackathon.bespring.domain.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon.bespring.domain.application.domain.Application;
import com.hackathon.bespring.domain.application.domain.repository.ApplicationRepository;
import com.hackathon.bespring.domain.application.presentation.dto.request.CallApplicationRequest;
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
import com.hackathon.bespring.global.util.UserUtil;
import com.hackathon.bespring.global.util.WebPushUtil;
import com.hackathon.bespring.infrastructure.feign.MatchServer;
import com.hackathon.bespring.infrastructure.feign.dto.request.MatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final WebPushRepository webPushRepository;
    private final WebPushUtil webPushUtil;
    private final CategoryRepository categoryRepository;
    private final ApplicationRepository applicationRepository;
    private final MatchServer matchServer;

    public PhoneNumberResponse urgentApplication(UrgentApplicationRequest request) {
        List<User> userList = userRepository.findAllUser(request.getLatitude(), request.getLongitude());

        WebPushSendDto webPushSendDto = WebPushSendDto.builder()
                .title("?????? ????????? ????????? ????????? ?????????????????????")
                .body("????????? ???????????? ?????? ????????????")
                .link("http://localhost:3000/call?phoneNumber=" + request.getPhoneNumber())
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
                .title("????????? ????????? ????????? ?????????????????????")
                .body("????????? ???????????? ????????? ??????")
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

    public void callApplication(CallApplicationRequest request) {
        User user = userUtil.getCurrentUser();
        Application application = Application.builder()
                .phoneNumber(request.getPhoneNumber())
                .user(user)
                .build();

        applicationRepository.save(application);

        MatchRequest matchRequest = new MatchRequest(
                user.getName(), user.getLongitude(), user.getLatitude(), request.getPhoneNumber()
        );
        matchServer.match(matchRequest);
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
