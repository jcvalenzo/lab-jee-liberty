package com.eibs.playground.service;

import com.eibs.playground.model.Account;
import com.eibs.playground.repository.AccountRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAccounts() throws SQLException {
        return accountRepository.findAll();
    }

    public void registerAccount(String accountNumber, String holderName, String balance) throws SQLException {
        if (isBlank(accountNumber) || isBlank(holderName) || isBlank(balance)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        BigDecimal parsedBalance;
        try {
            parsedBalance = new BigDecimal(balance);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El saldo debe ser un numero valido.", exception);
        }
        if (parsedBalance.signum() < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        accountRepository.create(accountNumber.trim(), holderName.trim(), parsedBalance);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }
}
