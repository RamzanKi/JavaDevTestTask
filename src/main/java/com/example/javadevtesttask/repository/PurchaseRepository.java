package com.example.javadevtesttask.repository;

import com.example.javadevtesttask.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value = "SELECT p.name " +
            "FROM purchase p " +
            "JOIN user_purchase up ON p.id = up.purchase_id " +
            "WHERE up.purchase_date >= now() - interval '1 month' " +
            "GROUP BY p.name " +
            "ORDER BY SUM(up.count) DESC " +
            "LIMIT 1", nativeQuery = true)
    String findMostPurchasedProductName();

    @Query(value = "SELECT p.name " +
            "FROM purchase p " +
            "JOIN user_purchase up ON p.id = up.purchase_id " +
            "WHERE up.user_age = 18 " +
            "GROUP BY p.name " +
            "ORDER BY SUM(up.count) DESC " +
            "LIMIT 1;", nativeQuery = true)
    String findPopularIn18();

    Purchase findPurchaseByNameIgnoreCase(String name);
}
