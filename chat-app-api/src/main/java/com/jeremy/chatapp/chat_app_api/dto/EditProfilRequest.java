package com.jeremy.chatapp.chat_app_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditProfilRequest {
    private String username;
    private String description;
}
