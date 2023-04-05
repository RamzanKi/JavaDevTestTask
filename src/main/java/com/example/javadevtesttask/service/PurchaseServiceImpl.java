package com.example.javadevtesttask.service;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    PurchaseRepository purchaseRepository;

    @Override
    public UserPurchase addPurchase(UserPurchase purchase) {
       return purchaseRepository.save(purchase);
    }

    @Override
    public List<UserPurchase> getPurchases() {
        return purchaseRepository.findAll();
    }
}
