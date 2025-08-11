package com.minibank.entity;

import com.minibank.util.Money;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = "accountNumber")
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 빌더에서 쓰려면 필드가 실제로 있어야 합니다
    @Column(nullable = false, length = 16)
    private String accountNumber;

    @Column(nullable = false, length = 50)
    private String ownerName;

    @Column(nullable = false, precision = 19, scale = 0) // KRW 기준
    private BigDecimal balance;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 도메인 로직 캡슐화
    public void deposit(BigDecimal amount) {
        BigDecimal inc = Money.requirePositive(amount);
        this.balance = Money.normalize(this.balance).add(inc);
    }

    public void withdraw(BigDecimal amount) {
        BigDecimal dec = Money.requirePositive(amount);
        BigDecimal cur = Money.normalize(this.balance);
        if (cur.compareTo(dec) < 0) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        this.balance = cur.subtract(dec);
    }
}
