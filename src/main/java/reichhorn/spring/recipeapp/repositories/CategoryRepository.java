package reichhorn.spring.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import reichhorn.spring.recipeapp.model.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByDescription(String description);
}
