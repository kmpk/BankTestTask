package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.exception.AppException;
import com.github.kmpk.banktesttask.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
    public static final int EXPIRATION_TIME_MINUTES = 30;
    private final AccountRepository repository;
    private final ConcurrentHashMap<String, LocalDateTime> operationDateTimeStorage = new ConcurrentHashMap<>();

    @Override
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES) //TODO: extract
    public void interestDeposit() {
        log.info("depositing interest for all accounts");
        repository.depositInterestsForAllUsers(new BigDecimal("1.05"), new BigDecimal("2.07")); //TODO: extract
    }

    @Override
    public void transfer(int fromId, int toId, BigDecimal amount, LocalDateTime operationDateTime) {
        if (!repository.existsById(toId)) {
            throw new AppException("Recipient account does not exist");
        }

        String operationDateTimeString = fromId + "-" + operationDateTime.toString();
        if (operationDateTimeStorage.containsKey(operationDateTimeString)) {
            //Operation has been processed before;
            return;
        } else {
            operationDateTimeStorage.put(operationDateTimeString, LocalDateTime.now());
        }

        //order by ids to avoid deadlocks
        if (fromId < toId) {
            repository.substract(fromId, amount);
            repository.add(toId, amount);
        } else {
            repository.add(toId, amount);
            repository.substract(fromId, amount);
        }
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    void cleanUpOperationDateTimeStorage() {
        log.info("cleaning up operationDateTimeStorage");
        LocalDate now = LocalDate.now();
        operationDateTimeStorage.entrySet()
                .removeIf(e -> ChronoUnit.MINUTES.between(now, e.getValue()) > EXPIRATION_TIME_MINUTES);
    }
}
