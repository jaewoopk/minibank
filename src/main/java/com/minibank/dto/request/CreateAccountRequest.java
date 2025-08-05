package com.minibank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateAccountRequest {

    @NotBlank(message = "계좌번호는 필수입니다.")
    private String accountNumber;

    @NotBlank(message = "예금주 이름은 필수입니다.")
    private String ownerName;

    @NotNull(message = "초기 입금액은 필수입니다.")
    @Positive(message = "초기 입금액은 0보다 커야 합니다.")
    private BigDecimal initialDeposit;
}
