package com.project.product.domain;

import com.project.seller.domain.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Embedded
    private Price price;

    @Embedded
    private Stock stock;

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

    private Product(
        String name,
        Price price,
        Stock stock,
        String description,
        ProductCategory category,
        ProductStatus status,
        Seller seller
    ) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
        this.status = status;
        this.seller = seller;
    }

    public static Product of(
        String name,
        Integer price,
        Integer stock,
        String description,
        ProductCategory category,
        Seller seller
    ) {
        return new Product(
            name,
            Price.from(price),
            Stock.from(stock),
            description,
            category,
            ProductStatus.SELL,
            seller
        );
    }

    public Integer getPrice() {
        return price.getPrice();
    }

    public Integer getTotalQuantity() {
        return stock.getTotalQuantity();
    }

    public Integer getSalesQuantity() {
        return stock.getSalesQuantity();
    }

    public void updateProduct(
        String name,
        Integer price,
        String description,
        ProductCategory category,
        ProductStatus status
    ) {
        this.name = name;
        this.price = Price.from(price);
        this.description = description;
        this.category = category;
        this.status = status;
    }

    public void updateStock(int stock) {
        this.stock.updateStock(stock);
    }

    public void increase() {
        stock.increase();
    }
}
