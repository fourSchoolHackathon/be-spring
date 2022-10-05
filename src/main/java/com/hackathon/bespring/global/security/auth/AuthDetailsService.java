package com.hackathon.bespring.global.security.auth;

import com.hackathon.bespring.domain.user.domain.repository.UserRepository;
import com.hackathon.bespring.global.security.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByAccountId(username)
                .map(AuthDetails::new)
                .orElseThrow(() -> UserNotFound.EXCEPTION);
    }
}
