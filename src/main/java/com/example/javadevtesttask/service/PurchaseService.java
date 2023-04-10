package com.example.javadevtesttask.service;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    public String findMostPurchasedProductName() {
        return purchaseRepository.findMostPurchasedProductName();
    }

    public String findPopularIn18() {
       return purchaseRepository.findPopularIn18();
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase findPurchaseByName(String name) {
        return purchaseRepository.findPurchaseByNameIgnoreCase(name);
    }

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }
}
