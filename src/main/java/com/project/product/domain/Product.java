package com.project.product.domain;

import java.util.ArrayList;
import java.util.List;

import com.project.seller.domain.Seller;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Embedded
	private Price price;

	@Embedded
	private Amount amount;

	@Column
	private Integer sales;

	@Column
	private String description;

	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@OneToMany(mappedBy = "product")
	private final List<ProductImage> productImage = new ArrayList<>();

	private Product(String name, Price price, Amount amount, Integer sales, String description,
		ProductCategory category, ProductStatus status, Seller seller) {
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.sales = sales;
		this.description = description;
		this.category = category;
		this.status = status;
		this.seller = seller;
	}

	public static Product of(
		String name,
		Integer price,
		Integer amount,
		String description,
		ProductCategory category,
		Seller seller
	) {
		return new Product(
			name,
			Price.from(price),
			Amount.from(amount),
			0,
			description,
			category,
			ProductStatus.SELL,
			seller);
	}

	public Integer getPrice() {
		return price.getPrice();
	}

	public Integer getAmount() {
		return amount.getAmount();
	}

	public void updateProduct(
		String name,
		Integer price,
		Integer amount,
		String description,
		ProductCategory category,
		ProductStatus status
	) {
		this.name = name;
		this.price = Price.from(price);
		this.amount = Amount.from(amount);
		this.description = description;
		this.category = category;
		this.status = status;
	}

	public void increaseSales() {
		this.sales += 1;
	}

}
