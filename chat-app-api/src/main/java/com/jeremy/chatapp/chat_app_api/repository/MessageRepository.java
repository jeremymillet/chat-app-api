package com.jeremy.chatapp.chat_app_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeremy.chatapp.chat_app_api.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationIdOrderByTimestampAsc(Long conversationId);
    void deleteAllByConversationId(Long conversationId);
}
