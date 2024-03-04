package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    @Override
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void interestDeposit() {
        log.info("depositing interest for all accounts");
        repository.depositInterestsForAllUsers(new BigDecimal("1.05"), new BigDecimal("2.07"));
    }

    @Override
    public void transfer(int fromId, int toId, BigDecimal amount) {

    }
}
