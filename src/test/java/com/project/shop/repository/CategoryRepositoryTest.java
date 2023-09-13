package com.project.shop.repository;

import com.project.shop.entity.Category;
import com.project.shop.entity.Product;
import com.project.shop.repo.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void CategoryRepository_Save_ReturnCategory(){

        Category category = Category.builder().name("cat 1").build();


        category = categoryRepository.save(category);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isGreaterThan(0);


    }

    @Test
    public void CategoryRepository_FindAll_ReturnListCategory(){

        Category category1 = Category.builder().name("cat 1").build();
        Category category2 = Category.builder().name("cat 2").build();
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        List<Category> categories =  categoryRepository.findAll();;

        Assertions.assertThat(categories).isNotEmpty();
        Assertions.assertThat(categories.size()).isEqualTo(2);

    }

    @Test
    public void CategoryRepository_FindById_ReturnCategory(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Category category1 =  categoryRepository.findById(category.getId()).get();

        Assertions.assertThat(category1).isNotNull();


    }



    @Test
    public void CategoryRepository_UpdateCategory_ReturnCategory(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        Category Updatecategory = categoryRepository.findById(category.getId()).get();
        Updatecategory.setName("cat 2");

        categoryRepository.save(Updatecategory);

        Assertions.assertThat(Updatecategory).isNotNull();
        Assertions.assertThat(Updatecategory.getName()).isEqualTo("cat 2");

    }

    @Test
    public void CategoryRepository_DeleteCategory_ReturnNull(){

        Category category = Category.builder().name("cat 1").build();


        categoryRepository.save(category);

        categoryRepository.deleteById(category.getId());


        Optional<Category> category1 = categoryRepository.findById(category.getId());

        Assertions.assertThat(category1).isEmpty();


    }


}
