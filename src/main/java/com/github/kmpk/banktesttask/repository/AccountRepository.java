package com.github.kmpk.banktesttask.repository;

import com.github.kmpk.banktesttask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("""
            UPDATE Account u
            SET u.balance =
            CASE
                WHEN u.balance < u.initialBalance * ?2 THEN LEAST(u.balance * ?1, u.initialBalance * ?2)
                ELSE u.balance
            END
            """)
    @Modifying
    void depositInterestsForAllUsers(BigDecimal interest, BigDecimal interestFloor);
}