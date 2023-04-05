package com.example.javadevtesttask.repository;

import com.example.javadevtesttask.entity.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<UserPurchase, Long> {

}
