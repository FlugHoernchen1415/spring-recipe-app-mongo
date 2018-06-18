package reichhorn.spring.recipeapp.services;

import reichhorn.spring.recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(String recipeId, String ingredientId);
}
