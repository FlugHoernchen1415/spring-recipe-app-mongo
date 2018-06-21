package reichhorn.spring.recipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reichhorn.spring.recipeapp.commands.IngredientCommand;
import reichhorn.spring.recipeapp.commands.UnitOfMeasureCommand;
import reichhorn.spring.recipeapp.converters.IngredientCommandToIngredient;
import reichhorn.spring.recipeapp.converters.IngredientToIngredientCommand;
import reichhorn.spring.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import reichhorn.spring.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import reichhorn.spring.recipeapp.model.Ingredient;
import reichhorn.spring.recipeapp.model.Recipe;
import reichhorn.spring.recipeapp.repositories.reactive.RecipeReactiveRepository;
import reichhorn.spring.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    IngredientService ingredientService;

    // init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeReactiveRepository, unitOfMeasureReactiveRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("1");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

        //when
        assertEquals("3", ingredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");
        command.setUnitOfMeasure(new UnitOfMeasureCommand());
        command.getUnitOfMeasure().setId("1234");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals("3", savedCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }


    @Test
    public void testDeleteById() throws Exception {
        // given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();

        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        // ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        // when
        ingredientService.deleteById("1", "3");

        // then
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
}