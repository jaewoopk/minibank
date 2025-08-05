package com.minibank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DepositRequest {

    @NotBlank(message = "계좌번호는 필수입니다.")
    private String accountNumber;

    @NotNull(message = "입금 금액은 필수입니다.")
    @Positive(message = "입금 금액은 0보다 커야 합니다.")
    private BigDecimal amount;
}
