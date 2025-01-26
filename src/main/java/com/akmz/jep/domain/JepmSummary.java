package com.akmz.jep.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "JEPM_SSUMMARY")
public class JepmSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO", nullable = false)
    private Integer no;

    @Column(name = "USERNO", nullable = false)
    private Integer userNo;

    @Column(name = "CONTINUOUSDAY", nullable = false)
    private Integer continuousDay;

    @Column(name = "TOTALDAY")
    private Integer totalDay;

    @Column(name = "TOTALTIME")
    private Integer totalTime;

    @Column(name = "KANATYPE", nullable = false, length = 1)
    private char kanaType = 'H';

    @Column(name = "CURKANAH", nullable = false, length = 3)
    private String curKanaH = "あ";

    @Column(name = "CURKANAK", nullable = false, length = 3)
    private String curKanaK = "ア";

    @Column(name = "GOALTIME", nullable = false)
    private Integer goalTime;

    @Column(name = "IDATE", nullable = false)
    private LocalDateTime inputDate;

    // Foreign key mapping to JEPM_USER entity (assuming the 'JEPM_USER' table is another JPA entity)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERNO", referencedColumnName = "USERNO", insertable=false, updatable=false)
    private JepmUser jepmUser;

    @Builder
    public JepmSummary(Integer continuousDay, Integer totalDay, Integer totalTime, char kanaType, String curKanaH, String curKanaK, Integer goalTime, LocalDateTime inputDate, JepmUser jepmUser) {
        this.continuousDay = (continuousDay != null)?continuousDay:0;
        this.totalDay = (totalDay != null)?totalDay:0;
        this.totalTime = (totalTime != null)?totalTime:0;
        this.kanaType = kanaType;
        this.curKanaH = (curKanaH != null)?curKanaH:"あ";
        this.curKanaK = (curKanaK != null)?curKanaK:"ア";
        this.goalTime = (goalTime != null)?goalTime:10;
        this.inputDate = (inputDate != null)?inputDate:LocalDateTime.now();
        this.jepmUser = jepmUser;
    }
}
