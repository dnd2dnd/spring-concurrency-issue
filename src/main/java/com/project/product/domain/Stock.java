package com.project.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static com.project.product.ProductConstant.STOCK_INIT_IS_10000;
import static com.project.product.ProductConstant.STOCK_IS_POSITIVE;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock implements Serializable {

    @Column
    private Integer totalQuantity;

    @Column
    private Integer salesQuantity;

    private Stock(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
        this.salesQuantity = totalQuantity;
    }

    public static Stock from(Integer totalQuantity) {
        validatePositive(totalQuantity);
        validateMin(totalQuantity);
        return new Stock(totalQuantity);
    }

    private static void validatePositive(int stock) {
        if (stock < 0) {
            throw new InvalidParameterException(STOCK_IS_POSITIVE);
        }
    }

    private static void validateMin(int stock) {
        if (stock < 10000) {
            throw new InvalidParameterException(STOCK_INIT_IS_10000);
        }
    }

    public void updateStock(int quantity) {
        this.totalQuantity += quantity;
    }

    public void increase() {
        if (totalQuantity >= salesQuantity) {
            throw new IllegalArgumentException("재고 없음");
        }
        salesQuantity++;
    }
}
