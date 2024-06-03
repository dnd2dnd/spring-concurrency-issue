package com.project.coupon.domain;

import com.project.coupon.domain.vo.Status;
import com.project.coupon.exception.AlreadyUsedUserCouponException;
import com.project.coupon.exception.ExpiredUserCouponException;
import com.project.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    private MemberCoupon(Member member, Coupon coupon, LocalDateTime usedAt, Status status) {
        this.member = member;
        this.coupon = coupon;
        this.usedAt = usedAt;
        this.status = status;
    }

    public static MemberCoupon of(Member member, Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon, LocalDateTime.now(), Status.UNUSED);
        member.addCoupon(memberCoupon);
        return memberCoupon;
    }

    public void use(LocalDateTime currentTime) {
        validate(currentTime);
        status = Status.USED;
        usedAt = currentTime;
    }

    private void validate(LocalDateTime currentTime) {
        if (status.equals(Status.USED)) {
            throw new AlreadyUsedUserCouponException();
        }
        if (coupon.getEndAt().isBefore(currentTime)) {
            throw new ExpiredUserCouponException();
        }
    }
}
