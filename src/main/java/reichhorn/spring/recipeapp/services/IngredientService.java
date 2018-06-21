package reichhorn.spring.recipeapp.services;

import reactor.core.publisher.Mono;
import reichhorn.spring.recipeapp.commands.IngredientCommand;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String id);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteById(String recipeId, String ingredientId);
}
