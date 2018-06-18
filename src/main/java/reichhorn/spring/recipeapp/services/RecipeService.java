package reichhorn.spring.recipeapp.services;

import reichhorn.spring.recipeapp.commands.IngredientCommand;
import reichhorn.spring.recipeapp.commands.RecipeCommand;
import reichhorn.spring.recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(String recipeId);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String recipeId);

    void deleteById(String recipeId);
}
