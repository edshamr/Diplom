package com.productShop.inventarization.dataloader;

import com.productShop.inventarization.model.Product;
import com.productShop.inventarization.model.ProductCategory;
import com.productShop.inventarization.repos.ProductCategoryRepository;
import com.productShop.inventarization.repos.ProductHistoryRepository;
import com.productShop.inventarization.repos.ProductRepository;
import com.productShop.inventarization.repos.ProductStockRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    ProductHistoryRepository productHistoryRepository;
    ProductRepository productRepository;
    ProductStockRepository productStockRepository;
    ProductCategoryRepository productCategoryRepository;

    public DataLoader(ProductHistoryRepository productHistoryRepository, ProductRepository productRepository,
                      ProductStockRepository productStockRepository,
                      ProductCategoryRepository productCategoryRepository) {
        this.productHistoryRepository = productHistoryRepository;
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public void run(ApplicationArguments args) {
        if (productRepository.findAll().size() == 0) {
            List<ProductCategory> categoryList = List.of(
                ProductCategory.builder()
                    .name("Bakery")
                    .build(),
                ProductCategory.builder()
                    .name("Meat")
                    .build(),
                ProductCategory.builder()
                    .name("Drinks")
                    .build(),
                ProductCategory.builder()
                    .name("Sweet")
                    .build()
            );
            productCategoryRepository.saveAll(categoryList);

            List<Product> productList = List.of(
                Product.builder()
                    .name("Fanta")
                    .vendorCode("FN")
                    .image(
                        "https://okwine.ua/files/product/61b345336ebb0c2d53db5bdf/30a3fe38-4c76-4416-83a5-b40b708e36f5.full_fanta-fanta-apelsin-zh-b-0-33l.jpg")
                    .categories(
                        Set.of(
                            productCategoryRepository.findFirstByName("Sweet").orElse(new ProductCategory()))
                    )
                    .build(),
                Product.builder()
                    .name("Coca Cola")
                    .vendorCode("CoCla")
                    .image(
                        "https://content.silpo.ua/sku/ecommerce/48/480x480wwm/486807_480x480wwm_2d004d3b-5367-32a3-e3e3-b5103cd7b27f.png")
                    .categories(
                        Set.of(
                            productCategoryRepository.findFirstByName("Sweet").orElse(new ProductCategory()),
                            productCategoryRepository.findFirstByName("Drinks").orElse(new ProductCategory()))
                    )
                    .build(),
                Product.builder()
                    .name("Good meat")
                    .vendorCode("MT")
                    .image(
                        "https://www.redefinemeat.com/wp-content/uploads/2022/04/BLOG1.jpg")
                    .categories(
                        Set.of(
                            productCategoryRepository.findFirstByName("Meat").orElse(new ProductCategory()))
                    )
                    .build(),
                Product.builder()
                    .name("Pork")
                    .vendorCode("PRK")
                    .image(
                        "https://assets.farmison.com/images/recipe-detail-345/83389-835-flat-pork-loin.jpg")
                    .categories(
                        Set.of(
                            productCategoryRepository.findFirstByName("Meat").orElse(new ProductCategory()))
                    )
                    .build(),
                Product.builder()
                    .name("Cake")
                    .vendorCode("CK")
                    .image(
                        "https://www.popsci.com/uploads/2019/03/18/GHDDTIRYTR22T6DYZG6GGWUZCQ-scaled.jpg?auto=webp")
                    .categories(
                        Set.of(
                            productCategoryRepository.findFirstByName("Sweet").orElse(new ProductCategory()),
                            productCategoryRepository.findFirstByName("Bakery").orElse(new ProductCategory()))
                    )
                    .build()
            );

            productRepository.saveAll(productList);
        }
    }
}