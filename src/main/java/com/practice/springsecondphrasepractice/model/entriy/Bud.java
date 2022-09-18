package com.practice.springsecondphrasepractice.model.entriy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bud") //營業日歷史檔
public class Bud {
    @Id
    @Column(name = "bud_ymd")
    private String budYmd;

    @NotNull
    @Column(name = "bud_type")
    private String budType;

    @NotNull
    @Column(name = "bud_u_time")
    private LocalDateTime budUTime;
}
