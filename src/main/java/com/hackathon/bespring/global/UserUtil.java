package com.hackathon.bespring.global;

import com.hackathon.bespring.domain.user.domain.User;
import com.hackathon.bespring.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    public User getCurrentUser() {
        AuthDetails user = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
