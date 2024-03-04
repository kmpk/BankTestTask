package com.github.kmpk.banktesttask.service;

import java.math.BigDecimal;

public interface AccountService {

    void interestDeposit();

    void transfer(int fromId, int toId, BigDecimal amount);
}
