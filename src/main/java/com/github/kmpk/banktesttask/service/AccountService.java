package com.github.kmpk.banktesttask.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AccountService {

    void interestDeposit();

    void transfer(int fromId, int toId, BigDecimal amount, LocalDateTime operationDate);
}
