package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Bud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudRepository extends JpaRepository<Bud, String> {

    Bud findByBudYmd(String budYmd);

}
