package com.chatapp.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.api.dto.ConversationDTO;
import com.chatapp.api.entity.Conversation;
import com.chatapp.api.repository.ConversationRepository;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public ConversationDTO getOrCreateConversation(Long friendshipId) {
        Optional<Conversation> optionalConversation = conversationRepository.findByFriendshipId(friendshipId);

        Conversation conversation = optionalConversation.orElseGet(() -> {
            // Si la conversation n'existe pas, créer une nouvelle
            Conversation newConversation = new Conversation();
            newConversation.setFriendshipId(friendshipId);
            return conversationRepository.save(newConversation);
        });

        return new ConversationDTO(conversation.getId(), conversation.getFriendshipId());
    }
}
