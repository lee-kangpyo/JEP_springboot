package com.akmz.jep.config.jwt;

import com.akmz.jep.domain.JepmUser;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    //토큰 생성
    public String GenerateToken(String type, JepmUser user, Duration exp){
        Date now = new Date();
        return makeToken(type, new Date(now.getTime() + exp.toMillis()), user);
    }

    //토큰 생성
    private String makeToken(String type, Date expired, JepmUser user){
        Date now = new Date();
        JwtBuilder tokenBuilder = Jwts.builder()
                // 헤더 {typ:JWT}
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 토큰 발급자
                // .setIssuer(jwtProperties.getIssuer())
                // 발급일
                .setIssuedAt(now)
                //만료일
                .setExpiration(expired)
                // 제목
                .setSubject(user.getUserNo().toString());

        if("access".equals(type)){
            tokenBuilder.claim("userId", user.getUserId());
        }

        return tokenBuilder
                // 서명 -> 헤더 + 클레임 + 비밀 키로 해시를 만들어 헤더와 클레임이 조작이 되었는지 체크
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }


    //토큰 검증
    public Claims validtoken(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            String userNo = claims.get("sub", String.class);
            if (userNo == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰에 사용자 정보가 없습니다.");
            }else{
                return claims;
            }
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
    }

    private Claims getClaimsFromToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return jws.getBody();
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
    }
}
