package com.github.kmpk.banktesttask.repository;

import com.github.kmpk.banktesttask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}