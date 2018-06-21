package reichhorn.spring.recipeapp.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reichhorn.spring.recipeapp.commands.RecipeCommand;
import reichhorn.spring.recipeapp.model.Recipe;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String recipeId);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String recipeId);

    Mono<Void> deleteById(String recipeId);
}
