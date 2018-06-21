package reichhorn.spring.recipeapp.repositories.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reichhorn.spring.recipeapp.model.Category;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() throws Exception {
        Category category = new Category();
        category.setDescription("Asian");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByDescription() throws Exception {
        Category category = new Category();
        category.setDescription("Italian");

        categoryReactiveRepository.save(category).then().block();

        Category returnedCategory = categoryReactiveRepository.findByDescription("Italian").block();

        assertNotNull(returnedCategory.getId());
        assertEquals(returnedCategory.getDescription(), category.getDescription());
    }
}