package com.project.basket.domain;

import com.project.basket.exception.ProductQuantityExceededException;
import com.project.product.domain.Product;
import lombok.Getter;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static com.project.product.ProductConstant.STOCK_IS_POSITIVE;

@Getter
public class BasketProduct implements Serializable {
    private final Product product;
    private final Integer quantity;

    private BasketProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static BasketProduct of(Product product, Integer quantity) {
        validatePositive(quantity);
        validateProductAmount(product.getTotalQuantity(), quantity);
        return new BasketProduct(product, quantity);
    }

    private static void validatePositive(int quantity) {
        if (quantity < 0) {
            throw new InvalidParameterException(STOCK_IS_POSITIVE);
        }
    }

    private static void validateProductAmount(int productQuantity, int quantity) {
        if (quantity > productQuantity) {
            throw new ProductQuantityExceededException();
        }
    }

    public BasketProduct calculate(Integer value) {
        return BasketProduct.of(product, quantity + value);
    }
}
