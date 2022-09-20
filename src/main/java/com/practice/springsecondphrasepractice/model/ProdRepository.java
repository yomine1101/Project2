package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Bud;
import com.practice.springsecondphrasepractice.model.entriy.Prod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdRepository extends JpaRepository<Prod, String> {
    Prod findByProdId(String prodId);
    List<Prod> findByProdIdStartingWith(String prodKind);
    List<Prod> findByProdCcyStartingWith(String prodCcy);

}
