package reichhorn.spring.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import reichhorn.spring.recipeapp.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {


}
