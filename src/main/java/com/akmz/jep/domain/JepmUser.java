package com.akmz.jep.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "JEPM_USER")
public class JepmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERNO", nullable = false, updatable = false)
    private Integer userNo;

    @Column(name = "EMAIL", length = 50, columnDefinition = "VARCHAR(50) DEFAULT NULL COMMENT '이메일'")
    private String email;

    @Column(name = "USERID", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '유저 아이디'")
    private String userId;

    @Column(name = "PASSWORD", nullable = false, length = 60, columnDefinition = "VARCHAR(60) COMMENT '비밀번호'")
    private String password;

    @Column(name = "TYPE", length = 2, columnDefinition = "CHAR(2) DEFAULT NULL COMMENT '정식계정 : 00, 임시 계정:01'")
    private String type;

    @Column(name = "USEYN", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'Y' COMMENT '사용여부'")
    private String useYn = "Y";

    @Column(name = "IUSERID", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '생성자'")
    private String iUserId;

    @Column(name = "IDATE", columnDefinition = "DATETIME DEFAULT NULL COMMENT '생성일'")
    private LocalDateTime iDate;

    @Column(name = "MUSERID", length = 50, columnDefinition = "VARCHAR(50) DEFAULT NULL COMMENT '수정자'")
    private String mUserId;

    @Column(name = "MDATE", columnDefinition = "DATETIME DEFAULT NULL COMMENT '수정일'")
    private LocalDateTime mDate;

    @Column(name = "ACCESS", length = 200, columnDefinition = "VARCHAR(200) DEFAULT '' COMMENT '엑세스토큰'")
    private String access;

    @Column(name = "REFRESH", length = 200, columnDefinition = "VARCHAR(200) DEFAULT '' COMMENT '리프레쉬토큰'")
    private String refresh;

    @Builder
    public JepmUser(String email, String userId, String password, String type, String useYn, String iUserId,
                    LocalDateTime iDate, String mUserId, LocalDateTime mDate, String access, String refresh) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.type = type;
        this.useYn = useYn;
        this.iUserId = iUserId;
        this.iDate = iDate;
        this.mUserId = mUserId;
        this.mDate = mDate;
        this.access = access;
        this.refresh = refresh;
    }

    @Override
    public String toString() {
        return "JepmUser{" +
                "userNo=" + userNo +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", useYn='" + useYn + '\'' +
                ", iUserId='" + iUserId + '\'' +
                ", iDate=" + iDate +
                ", mUserId='" + mUserId + '\'' +
                ", mDate=" + mDate +
                ", access='" + access + '\'' +
                ", refresh='" + refresh + '\'' +
                '}';
    }
}
