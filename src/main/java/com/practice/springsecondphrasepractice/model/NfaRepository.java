package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entriy.Nfa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NfaRepository extends JpaRepository<Nfa, String> {
}
