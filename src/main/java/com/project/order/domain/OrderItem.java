package com.project.order.domain;

import com.project.product.domain.Price;
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
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	private Integer quantity;

	@Embedded
	private Price price;

	private OrderItem(Order order, Product product, Integer quantity, Price price) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
	}

	public static OrderItem of(Order order, Product product, Integer quantity, Price price) {
		return new OrderItem(order, product, quantity, price);
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
