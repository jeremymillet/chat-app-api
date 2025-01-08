package com.chatapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendDTO {
    private Long friendshipId;
    private Long friendId;
    private String friendName;

}
