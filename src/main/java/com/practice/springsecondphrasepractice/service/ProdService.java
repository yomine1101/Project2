package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.model.ProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdService {

    @Autowired
    ProdRepository prodRepository;
}
