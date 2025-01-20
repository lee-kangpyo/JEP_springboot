package com.akmz.jep.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveUserRequest {
    private String userId;
    private String password;
    private String email;
    private String tempId;

    @Override
    public String toString() {
        return "SaveUserRequest{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", tempId='" + tempId + '\'' +
                '}';
    }
}
