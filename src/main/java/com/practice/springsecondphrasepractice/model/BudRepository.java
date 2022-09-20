package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Bud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudRepository extends JpaRepository<Bud, String> {

    Bud findByBudYmd(String budYmd);

    List<Bud> findByBudYmdBetween(String startDate, String endDate);

    List<Bud> findByBudYmdStartingWith(String year);

    @Query(value = "SELECT * FROM bud WHERE bud_ymd < ?1 AND bud_type = 'Y' ORDER BY bud_ymd DESC LIMIT 1;", nativeQuery = true)
    List<Bud> getPrevYmd(String year);

    //正序
    @Query(value = "SELECT * FROM bud WHERE bud_ymd > ?1 AND bud_type = 'Y' ORDER BY bud_ymd ASC LIMIT 1",nativeQuery = true)
    List<Bud> getNextYmd(String year);

}
