package com.jeremy.chatapp.chat_app_api.Service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Pour COMMIT 2, on ne fait rien encore
        throw new UnsupportedOperationException("Service non implémenté pour COMMIT 2");
    }
}
