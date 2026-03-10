package com.jeremy.chatapp.chat_app_api.service;

import com.jeremy.chatapp.chat_app_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Rechercher l'utilisateur avec l'email
        com.jeremy.chatapp.chat_app_api.entity.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Mapper l'utilisateur avec le modèle attendu par Spring Security
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_USER") // Définir les autorisations par défaut
                .build();
    }
}
