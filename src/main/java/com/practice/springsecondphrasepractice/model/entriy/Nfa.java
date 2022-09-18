package com.practice.springsecondphrasepractice.model.entriy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nfa") //公告管理
public class Nfa {

    @Id
    @Column(name = "nfa_uuid")
    private String nfaUuid;

    @NotNull
    @Column(name = "nfa_subject")
    private String nfaSubject;

    @NotNull
    @Column(name = "nfa_content")
    private String nfaContent;

    @NotNull
    @Column(name = "nfa_enable")
    private String nfaEnable;

    @NotNull
    @Column(name = "nfa_s_time")
    private String nfaSTime;

    @NotNull
    @Column(name = "nfa_e_time")
    private String nfaETime;

    @NotNull
    @Column(name = "nfa_u_time")
    private String nfaUTime;
}
