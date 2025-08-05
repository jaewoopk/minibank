package com.minibank.controller;

import com.minibank.dto.request.CreateAccountRequest;
import com.minibank.dto.request.DepositRequest;
import com.minibank.dto.request.WithdrawRequest;
import com.minibank.dto.response.AccountResponse;
import com.minibank.entity.Account;
import com.minibank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        Account account = accountService.createAccount(
                request.getAccountNumber(),
                request.getOwnerName(),
                request.getInitialDeposit()
        );
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountResponse> deposit(@RequestBody @Valid DepositRequest request) {
        Account account = accountService.deposit(
                request.getAccountNumber(),
                request.getAmount()
        );
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@RequestBody @Valid WithdrawRequest request) {
        Account account = accountService.withdraw(
                request.getAccountNumber(),
                request.getAmount()
        );
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
