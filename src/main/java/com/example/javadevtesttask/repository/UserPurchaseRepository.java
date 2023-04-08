package com.example.javadevtesttask.repository;

import com.example.javadevtesttask.entity.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
    List<UserPurchase> findByPurchaseDateAfter(LocalDate date);

    @Query(value = "SELECT user_name, user_lastname " +
            "FROM user_purchase " +
            "WHERE purchase_date >= now() - interval '6 month' " +
            "GROUP BY user_name, user_lastname " +
            "ORDER BY SUM(count) DESC " +
            "LIMIT 1; ", nativeQuery = true)
    String findMostActiveCustomer();
}
