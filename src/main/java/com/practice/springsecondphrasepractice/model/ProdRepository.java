package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Prod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdRepository extends JpaRepository<Prod, String> {
}
