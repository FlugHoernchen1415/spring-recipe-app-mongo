package reichhorn.spring.recipeapp.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reichhorn.spring.recipeapp.model.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
