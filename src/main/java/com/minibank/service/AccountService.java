package com.minibank.service;

import com.minibank.entity.Account;
import com.minibank.exception.AccountAlreadyExistsException;
import com.minibank.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(String accountNumber, String ownerName, BigDecimal initialDeposit) {
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountAlreadyExistsException("이미 존재하는 계좌번호입니다: " + accountNumber);
        }

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .ownerName(ownerName)
                .balance(initialDeposit)
                .build();

        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        account.setBalance(account.getBalance().add(amount));
        return account;
    }

    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return account;
    }

    @Transactional
    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        accountRepository.delete(account);
    }
}
