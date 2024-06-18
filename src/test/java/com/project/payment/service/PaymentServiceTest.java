package com.project.payment.service;

import com.project.product.domain.Product;
import com.project.product.domain.ProductCategory;
import com.project.product.dto.request.ProductCreateRequest;
import com.project.product.repository.ProductRepository;
import com.project.product.service.ProductService;
import com.project.seller.domain.Seller;
import com.project.seller.repository.SellerRepository;
import com.project.seller.service.SellerService;
import com.project.user.domain.User;
import com.project.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void init() {
        User user = createUser();
        Seller seller = createSeller();
        Product product = createProduct(seller);

    }

    @Test
    void 상품100개씩_100명이_주문에_성공한다() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i=0; i<100; i++) {

        }
    }

    private User createUser() {
        String email = "test@naver.com";
        String nickname = "test";
        String password = "test123!";
        User user = User.of(email, nickname, password, passwordEncoder);
        return userRepository.save(user);
    }

    private Seller createSeller() {
        String email = "buisiness@naver.com";
        String password = "test123!@#$";
        String taxpayerIdentificationNum = "111-11-11111";
        String companyName = "하루야채";
        String businessLocation = "서울특별시 영등포구 1111길 하루야채";

        Seller seller = Seller.of(email, password, passwordEncoder, taxpayerIdentificationNum, companyName, businessLocation);
        return sellerRepository.save(seller);
    }

    private Product createProduct(Seller seller) {
        String name = "당근";
        Integer price = 10000;
        Integer stock = 10000;
        String description = "당근 설명";
        ProductCategory category = ProductCategory.FRESH;
        Long sellerId = seller.getId();
        ProductCreateRequest productCreateRequest = new ProductCreateRequest(
            name,
            price,
            stock,
            description,
            category,
            sellerId
        );
        Long productId = productService.createProduct(productCreateRequest);
        return productRepository.getById(productId);
    }
}