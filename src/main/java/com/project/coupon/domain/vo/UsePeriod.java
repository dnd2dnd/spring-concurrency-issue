package com.project.coupon.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsePeriod {
    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;

    private UsePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static UsePeriod of(LocalDateTime startAt, LocalDateTime endAt) {
        return new UsePeriod(startAt, endAt);
    }
}
