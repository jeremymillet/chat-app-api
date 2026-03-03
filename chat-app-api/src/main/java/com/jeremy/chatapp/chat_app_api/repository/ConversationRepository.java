package com.jeremy.chatapp.chat_app_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeremy.chatapp.chat_app_api.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    Optional<Conversation> findByFriendshipId(Long friendshipId);
    void deleteByFriendshipId(Long friendshipId);
    
}
