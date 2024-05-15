package com.project.coupon;

import com.project.product.domain.Price;
import com.project.product.domain.Product;
import com.project.product.domain.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * - 쿠폰 아이디, Long , NN, PK
 * - 판매자 아이디, Long , NN, FK
 * - 상품 아이디, Long, NN, FK
 * - 쿠폰 이름  ,String, NN
 * - 쿠폰 내용, String, NN
 * - 쿠폰 소멸일 ,DateTime, NN
 * - 쿠폰 수량, Integer, NN
 * - 쿠폰 조건, Integer, N
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Embedded
	private DiscountPolicy discountPolicy;

	@Embedded
	private UsePeriod usePeriod;

	@Column
	private Stock stock;

	@Column
	private Price minPrdouctPrice;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private Coupon(String name, DiscountPolicy discountPolicy, UsePeriod usePeriod, Stock stock, Price minPrdouctPrice, Product product) {
		this.name = name;
		this.discountPolicy = discountPolicy;
		this.usePeriod = usePeriod;
		this.stock = stock;
		this.minPrdouctPrice = minPrdouctPrice;
		this.product = product;
	}

	public Coupon of(String name, DiscountPolicy discountPolicy, UsePeriod usePeriod, Stock stock, Price minPrdouctPrice, Product product) {
		return new Coupon(name, discountPolicy, usePeriod, stock, minPrdouctPrice, product);
	}
}
