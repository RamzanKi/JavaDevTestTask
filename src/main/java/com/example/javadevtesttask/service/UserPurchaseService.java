package com.example.javadevtesttask.service;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.repository.UserPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserPurchaseService {

    @Autowired
    UserPurchaseRepository userpurchaseRepository;

    public UserPurchase addPurchase(UserPurchase purchase) {
       return userpurchaseRepository.save(purchase);
    }

    public List<UserPurchase> findAll() {
        return userpurchaseRepository.findAll();
    }

    public List<UserPurchase> findByPurchaseDateAfter(LocalDate date) {
        return userpurchaseRepository.findByPurchaseDateAfter(date);
    }

    public String findMostActiveCustomer() {
        return userpurchaseRepository.findMostActiveCustomer();
    }

    public UserPurchase getById(Long id) {
        return userpurchaseRepository.getById(id);
    }

    public UserPurchase save(UserPurchase userPurchase) {
        return userpurchaseRepository.save(userPurchase);
    }

    public void deleteById(Long id) {
        userpurchaseRepository.deleteById(id);
    }
}
