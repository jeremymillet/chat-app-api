package com.chatapp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.api.entity.Conversation;
import com.chatapp.api.repository.ConversationRepository;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation getOrCreateConversation(Long friendshipId) {
        return conversationRepository.findByFriendshipId(friendshipId)
                .orElseGet(() -> {
                    // Si la conversation n'existe pas, créer une nouvelle conversation
                    Conversation conversation = new Conversation();
                    conversation.setFriendshipId(friendshipId);
                    return conversationRepository.save(conversation);
                });
    }
}
