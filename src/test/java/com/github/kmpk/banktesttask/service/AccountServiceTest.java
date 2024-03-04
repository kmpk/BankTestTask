package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.model.Account;
import com.github.kmpk.banktesttask.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "classpath:prepare_test_data.sql")
@ActiveProfiles("test")
class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void transfer() {
        accountService.transfer(1, 2, new BigDecimal("100.00"), LocalDateTime.now());
        Assertions.assertEquals(new BigDecimal("900.00"), accountRepository.findById(1).get().getBalance());
        Assertions.assertEquals(new BigDecimal("1100.00"), accountRepository.findById(2).get().getBalance());
    }

    @Test
    void transferInsufficientFunds() {
        DataIntegrityViolationException ex = Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> accountService.transfer(1, 2, new BigDecimal("1000.01"), LocalDateTime.now()));
        Assertions.assertTrue(ex.getMessage().contains("account_balance_check"));
    }

    @Test
    void transferConcurrent() {
        AtomicBoolean exceptionOccured = new AtomicBoolean(false);

        List<CompletableFuture<?>> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(CompletableFuture.runAsync(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int fromId = random.nextInt(1, 11);
                int toId = fromId;
                while (toId == fromId) {
                    toId = random.nextInt(1, 11);
                }

                int month = random.nextInt(1, 13);
                int day = random.nextInt(1, 29);
                int hour = random.nextInt(24);
                int minute = random.nextInt(60);

                LocalDateTime operationDate = LocalDateTime.of(2000, month, day, hour, minute);
                accountService.transfer(fromId, toId, new BigDecimal("100.10"), operationDate);
            }).exceptionally(ex -> {
                if (!ex.getMessage().endsWith("[account_balance_check]")) {
                    exceptionOccured.set(true);
                }
                return null;
            }));
        }
        CompletableFuture.allOf(list.toArray(CompletableFuture[]::new)).join();
        Assertions.assertFalse(exceptionOccured.get());

        BigDecimal sum = accountRepository.findAll()
                .stream()
                .map(Account::getBalance)
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
        Assertions.assertEquals(new BigDecimal("10000.00"), sum);
    }
}