package com.example.javadevtesttask.repository;

import com.example.javadevtesttask.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @Query(value = "SELECT purchase.name\n" +
            "FROM purchase\n" +
            "         JOIN user_purchase ON purchase.id = user_purchase.purchase_id\n" +
            "WHERE user_purchase.user_age = 18\n" +
            "GROUP BY purchase.name\n" +
            "ORDER BY SUM(user_purchase.count) DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    String findPopularIn18();
}
