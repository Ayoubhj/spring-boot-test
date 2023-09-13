package com.project.shop.repository;

import com.project.shop.entity.Category;
import com.project.shop.entity.Product;
import com.project.shop.repo.CategoryRepository;
import com.project.shop.repo.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void ProductRepository_Save_ReturnProduct(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);

        Product product =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();

        productRepository.save(product);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isGreaterThan(0);

    }

    @Test
    public void ProductRepository_FindAll_ReturnListProduct(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);

        Product product1 =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();
        Product product2 =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.size()).isEqualTo(2);

    }

    @Test
    public void ProductRepository_FindById_ReturnProduct(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);

        Product product1 =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();

        productRepository.save(product1);

        Product product = productRepository.findById(product1.getId()).get();

        Assertions.assertThat(product).isNotNull();


    }

    @Test
    public void ProductRepository_FindByCategoryId_ReturnProduct(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);

        Product product1 =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();

        productRepository.save(product1);

        List<Product> product = productRepository.getProductByCategoryId(category.getId());

        Assertions.assertThat(product).isNotEmpty();

    }

    @Test
    public void ProductRepository_UpdateProduct_ReturnProduct(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);

        Product product1 =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();

        productRepository.save(product1);

        Product product = productRepository.findById(product1.getId()).get();
        product.setTitle("AZRTY");
        product.setPrice("478");
        Product Updateproduct = productRepository.save(product) ;

        Assertions.assertThat(Updateproduct.getTitle()).isNotNull();
        Assertions.assertThat(Updateproduct.getPrice()).isNotNull();
    }

    @Test
    public void ProductRepository_DeleteProduct_ReturnNull(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);

        Product product1 =  Product.builder().title("air force").price("400").quantity("200").descreption("test").category(category).build();

        productRepository.save(product1);
        productRepository.deleteById(product1.getId());

        Optional<Product> product = productRepository.findById(product1.getId());

        Assertions.assertThat(product).isEmpty();

    }

}
