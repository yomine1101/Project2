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
@Table(name = "prod") //商品
public class Prod {

    @Id
    @Column(name = "prod_id")
    private String prodId;

    @NotNull
    @Column(name = "prod_kind")
    private String prodKind;

    @NotNull
    @Column(name = "prod_name")
    private String prodName;

    @NotNull
    @Column(name = "prod_ename")
    private String prodEname;

    @NotNull
    @Column(name = "prod_ccy")
    private String prodCcy;

    @NotNull
    @Column(name = "prod_enable")
    private String prodEnable;

    @NotNull
    @Column(name = "prod_i_time")
    private LocalDateTime prodITime;

    @NotNull
    @Column(name = "prod_u_time")
    private LocalDateTime prodUTime;
}
