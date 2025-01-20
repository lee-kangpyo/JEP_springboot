package com.akmz.jep.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String status_code;
    private String access_token;
    private String refresh_token;
    private String token_type;

    @Builder
    public LoginResponse(String status_code, String access_token, String refresh_token, String token_type) {
        this.status_code = status_code != null ? status_code : "200";
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.token_type = token_type != null ? token_type : "Bearer";
    }
}
