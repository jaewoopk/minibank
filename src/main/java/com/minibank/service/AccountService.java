package com.minibank.service;

import com.minibank.entity.Account;
import com.minibank.exception.AccountAlreadyExistsException;
import com.minibank.repository.AccountRepository;
import com.minibank.util.Money;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        account.deposit(amount);
        return account;
    }

    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        account.withdraw(amount);
        return account;
    }


    @Transactional
    public Account createAccount(String accountNumber, String ownerName, BigDecimal initialDeposit) {
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountAlreadyExistsException("이미 존재하는 계좌번호입니다: " + accountNumber);
        }
        // DTO에서 @Positive지만, 서비스도 2중 방어
        BigDecimal init = Money.requirePositive(initialDeposit);

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .ownerName(ownerName)
                .balance(init) // null 방지
                .build();
        return accountRepository.save(account);
    }


    @Transactional
    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
        accountRepository.delete(account);
    }
}
