package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Nfa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NfaRepository extends JpaRepository<Nfa, String> {
    Nfa findByNfaUuid(String nfaUuid);

    //非必要 三擇一

    List<Nfa> findByNfaSubject(String subject);
    List<Nfa> findByNfaSTime(String startDate);
    List<Nfa> findByNfaETime(String endDate);

    /* JPA Like 有點絕對 Containing 參數前後加% (我都要) */
//    List<Nfa> findByNfaSubjectLike(@Param("subject") String subject);

    List<Nfa> findByNfaSubjectContaining(String subject);

}
