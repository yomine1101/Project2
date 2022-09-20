package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Nfa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NfaRepository extends JpaRepository<Nfa, String> {
    Nfa findByNfaUuid(String nfaUuid);

    //非必要 二擇一
    List<Nfa> findByNfaSTime(String startDate);
    List<Nfa> findByNfaETime(String endDate);

    List<Nfa> findByNfaSubjectContaining(String subject);

}
