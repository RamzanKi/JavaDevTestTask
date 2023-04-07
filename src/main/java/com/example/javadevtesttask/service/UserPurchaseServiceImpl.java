package com.example.javadevtesttask.service;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.repository.UserPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPurchaseServiceImpl {

    @Autowired
    UserPurchaseRepository userpurchaseRepository;

    public UserPurchase addPurchase(UserPurchase purchase) {
       return userpurchaseRepository.save(purchase);
    }

    public List<UserPurchase> getPurchases() {
        return userpurchaseRepository.findAll();
    }
}
