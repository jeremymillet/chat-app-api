package com.chatapp.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatapp.api.dto.UserDTO;
import com.chatapp.api.entity.Friend;
import com.chatapp.api.entity.User;
import com.chatapp.api.repository.FriendRepository;
import com.chatapp.api.repository.UserRepository;
@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllFriends(Long userId) {
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        return friends.stream()
                .map(friend -> {
                    User friendUser = userRepository.findById(friend.getFriendId())
                            .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
                    return new UserDTO(
                            friendUser.getId(),
                            friendUser.getUsername(),
                            friendUser.getEmail());
                })
                .collect(Collectors.toList());
    }
    
    public Friend sendFriendRequest(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        // Vérifier si la demande d'amitié existe déjà
        if (friendRepository.existsByUserIdAndFriendId(userId, friendId)
                || friendRepository.existsByUserIdAndFriendId(friendId, userId)) {
            throw new IllegalArgumentException("Friend request already exists");
        }

        // Créer une nouvelle demande d'amitié
        Friend friendRequest = new Friend(userId, friendId, false); // false signifie que la demande n'est pas acceptée
        return friendRepository.save(friendRequest);
    }

    // Accepter la demande d'amitié
    public Friend acceptFriendRequest(Long friendId, Long userId) {
        Friend friendRequest = friendRepository.findByUserIdAndFriendIdAndAccepted(userId, friendId, false)
                .orElseThrow(() -> new IllegalArgumentException("No pending friend request found"));

        friendRequest.setAccepted(true);
        return friendRepository.save(friendRequest);
    }
    
    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId, friendId);
        friendRepository.deleteByUserIdAndFriendId(friendId, userId);
    }
}
