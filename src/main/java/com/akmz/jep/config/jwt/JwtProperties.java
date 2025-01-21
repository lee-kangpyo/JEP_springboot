package com.akmz.jep.config.jwt;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String secretKey;

    public byte[] getSecretKey() {
        return secretKey.getBytes();
    }
}
