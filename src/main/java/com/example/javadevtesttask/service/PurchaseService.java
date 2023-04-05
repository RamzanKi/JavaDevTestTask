package com.example.javadevtesttask.service;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.entity.UserPurchase;

import java.util.List;

public interface PurchaseService {
    public UserPurchase addPurchase(UserPurchase purchase);

    public List<UserPurchase> getPurchases();
}
