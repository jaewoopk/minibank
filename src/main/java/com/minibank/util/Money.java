package com.minibank.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {
    private Money() {}

    public static BigDecimal zero() { return BigDecimal.ZERO.setScale(0); } // KRW

    public static BigDecimal normalize(BigDecimal v) {
        if (v == null) return zero();
        // KRW라면 scale(0), 소수 허용 통화라면 scale(2/4 등)로 통일
        return v.setScale(0, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal requirePositive(BigDecimal v) {
        BigDecimal n = normalize(v);
        if (n.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }
        return n;
    }
}
