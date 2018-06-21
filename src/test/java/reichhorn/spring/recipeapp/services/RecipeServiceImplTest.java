package reichhorn.spring.recipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reichhorn.spring.recipeapp.converters.RecipeCommandToRecipe;
import reichhorn.spring.recipeapp.converters.RecipeToRecipeCommand;
import reichhorn.spring.recipeapp.exceptions.NotFoundException;
import reichhorn.spring.recipeapp.model.Recipe;
import reichhorn.spring.recipeapp.repositories.RecipeRepository;
import reichhorn.spring.recipeapp.repositories.reactive.RecipeReactiveRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.findById("1").block();

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(recipes.size(), 1);

        // Verify that the findAll Method gets called exactly one time
        verify(recipeReactiveRepository, times(1)).findAll();
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void deleteByIdTest() throws Exception {

        // given
        String id = "2";

        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        // when
        recipeService.deleteById(id);

        // no 'when', since method has void return type

        // then
        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}