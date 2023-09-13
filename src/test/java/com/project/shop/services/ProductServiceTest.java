package com.project.shop.services;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.project.shop.entity.Category;
import com.project.shop.entity.Product;
import com.project.shop.repo.CategoryRepository;
import com.project.shop.repo.ProductRepository;
import com.project.shop.request.ProductRequest;
import com.project.shop.services.impl.ProductImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductImpl productImpl;

    private Category category;
    private Product product;
    private Product product1;
    private Product product2;

    private ProductRequest request;
    @BeforeEach
    public void init(){
        category = Category.builder().id(1L).name("cat 1").build();
        product1 = Product.builder().title("air force").price("200").quantity("200").category(category).descreption("azer").build();
        product2 = Product.builder().title("air force").price("200").quantity("200").category(category).descreption("azer").build();
        product = Product.builder().title("air force").price("200").quantity("200").category(category).descreption("azer").build();
        request = ProductRequest.builder().title("air force").price("200").quantity("200").category(category).descreption("azer").build();
    }
    @Test
    public void ProductService_Get_All_Product_ReturnProduct(){

        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        productRepository.save(product1);
        productRepository.save(product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result =  productImpl.getAllProducts();


        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).isNotEmpty();

    }


    @Test
    public void ProductService_Create_Product_ReturnProduct(){

        ProductRequest request = ProductRequest.builder().title("air force").price("200").quantity("200").category(category).descreption("azer").build();

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ObjectNode result =  productImpl.createdProduct(request);

        TextNode text = TextNode.valueOf("Product Created  Successfuly");

        Assertions.assertThat(result.get("message")).isEqualTo(text);

    }



    @Test
    public void ProductService_Update_Product_ReturnProduct(){


        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ObjectNode result1 =  productImpl.UpdateProduct(request, 1L);
        ObjectNode result2 =  productImpl.UpdateProduct(request, 2L);


        TextNode text1 = TextNode.valueOf("Product Updated  Successfuly");
        TextNode text2 = TextNode.valueOf("Product Not Found");

        Assertions.assertThat(result1.get("message")).isEqualTo(text1);
        Assertions.assertThat(result2.get("message")).isEqualTo(text2);
    }

    @Test
    public void ProductService_Delete_Product_ReturnProduct(){



        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));

        ObjectNode result1 =  productImpl.DeleteProduct(1L);
        ObjectNode result2 =  productImpl.DeleteProduct(2L);


        TextNode text1 = TextNode.valueOf("Product Deleted  Successfuly");
        TextNode text2 = TextNode.valueOf("Product Not Found");

        Assertions.assertThat(result1.get("message")).isEqualTo(text1);
        Assertions.assertThat(result2.get("message")).isEqualTo(text2);
    }
}
