package com.project.product.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	private String name;

	@Embedded
	private Price price;

	@Embedded
	private Amount amount;

	private Integer sales;

	private String desc;

	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@OneToMany(mappedBy = "product")
	private final List<ProductImage> productImage = new ArrayList<>();

	private Product(String name, Price price, Amount amount, Integer sales, String desc, ProductCategory category,
		ProductStatus status) {
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.sales = sales;
		this.desc = desc;
		this.category = category;
	}

	public static Product of(String name, Integer price, Integer amount, String desc, ProductCategory category,
		ProductStatus status) {
		return new Product(
			name,
			Price.from(price),
			Amount.from(amount),
			0,
			desc,
			category,
			status
		);
	}

	public void updateProduct(
		String name,
		Integer price,
		Integer amount,
		String desc,
		ProductCategory category,
		ProductStatus status
	) {
		this.name = name;
		this.price = Price.from(price);
		this.amount = Amount.from(amount);
		this.desc = desc;
		this.category = category;
		this.status = status;
	}

	public void increaseSales() {
		this.sales += 1;
	}
}
