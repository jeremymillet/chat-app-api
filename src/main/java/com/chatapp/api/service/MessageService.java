package com.chatapp.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.api.dto.MessageDTO;
import com.chatapp.api.entity.Message;
import com.chatapp.api.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(MessageDTO messageDTO){
        Message message = convertToMessage(messageDTO);
        return messageRepository.save(message);
    }

    public List<MessageDTO> getMessages(Long conversationId) {
        List<Message> messages = messageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
        // Conversion de Message en MessageDTO
        return messages.stream()
                .map(this::convertToMessageDTO)
                .collect(Collectors.toList());
    }

    public void markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        message.setIsRead(true);
        messageRepository.save(message);
    }

    private MessageDTO convertToMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSenderId(message.getSenderId());
        messageDTO.setContent(message.getContent());

        // Convertir le timestamp (Long) en LocalDateTime
        LocalDateTime timestamp = Instant.ofEpochMilli(message.getTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        messageDTO.setTimestamp(timestamp);

        messageDTO.setIsRead(message.isRead());
        return messageDTO;
    }
    
    private Message convertToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setSenderId(messageDTO.getSenderId());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(System.currentTimeMillis()); // Ajoute un timestamp si nécessaire
        message.setIsRead(false); // Valeur par défaut pour isRead
        return message;
    }
}
