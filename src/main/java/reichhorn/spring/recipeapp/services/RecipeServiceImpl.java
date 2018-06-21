package reichhorn.spring.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reichhorn.spring.recipeapp.commands.RecipeCommand;
import reichhorn.spring.recipeapp.converters.RecipeCommandToRecipe;
import reichhorn.spring.recipeapp.converters.RecipeToRecipeCommand;
import reichhorn.spring.recipeapp.model.Recipe;
import reichhorn.spring.recipeapp.repositories.reactive.RecipeReactiveRepository;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String recipeId) {
        return recipeReactiveRepository.findById(recipeId);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String recipeId) {
        return recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipeCommand.getId());
                    });

                    return recipeCommand;
                });
    }

    @Override
    public Mono<Void> deleteById(String recipeId) {
        recipeReactiveRepository.deleteById(recipeId).block();

        return Mono.empty();
    }
}
