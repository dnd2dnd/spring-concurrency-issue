package com.project.product.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

	private String desc;

	private ProductCategory category;

	@OneToMany(mappedBy = "product")
	private final List<ProductImage> productImage = new ArrayList<>();

	private Product(String name, Price price, Amount amount, String desc, ProductCategory category) {
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.desc = desc;
		this.category = category;
	}

	public static Product of(String name, Integer price, Integer amount, String desc, ProductCategory category) {
		return new Product(
			name,
			Price.from(price),
			Amount.from(amount),
			desc,
			category
		);
	}
}
