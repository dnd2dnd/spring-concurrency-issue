package com.project.order.domain;

import com.project.product.domain.Amount;
import com.project.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Embedded
	Amount amount;

	private OrderItem(Order order, Product product, Amount amount) {
		this.order = order;
		this.product = product;
		this.amount = amount;
	}

	public static OrderItem of(Order order, Product product, Integer amount) {
		return new OrderItem(order, product, Amount.from(amount));
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
