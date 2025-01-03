package com.example.WhizzRecipe.controller;

import java.util.*;
import com.example.WhizzRecipe.repository.RecipeRepository;
import com.example.WhizzRecipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.WhizzRecipe.dto.Recipe;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {


	private final RecipeService recipeService;
	@Autowired
	private final RecipeRepository recipeRepository;


	public RecipeController(RecipeService recipeService, RecipeRepository recipeRepository) {
		this.recipeService = recipeService;
		this.recipeRepository = recipeRepository;
	}

	@GetMapping
	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = recipeRepository.findAll();
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No recipes found.");
		}
		return recipes;
	}

	@GetMapping("/search")
	public List<Recipe> searchRecipes(@RequestParam String query) {
		List<Recipe> recipes = recipeRepository.findByTitleContainingIgnoreCase(query);
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No recipes found for the given query.");
		}
		return recipes;
	}

	@GetMapping("/gluten-free")
	public List<Recipe> getGlutenFree() {
		List<Recipe> recipes = recipeRepository.findByGlutenFreeTrue();
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No gluten-free recipes found.");
		}
		return recipes;
	}

	@GetMapping("/vegan")
	public List<Recipe> getVegan() {
		List<Recipe> recipes = recipeRepository.findByVeganTrue();
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No vegan recipes found.");
		}
		return recipes;
	}

	@GetMapping("/vegetarian")
	public List<Recipe> getVegetarian() {
		List<Recipe> recipes = recipeRepository.findByVegetarianTrue();
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No vegetarian recipes found.");
		}
		return recipes;
	}

	@GetMapping("/vegan-and-gluten-free")
	public List<Recipe> getVeganAndGlutenFree() {
		List<Recipe> recipes = recipeRepository.findByVeganTrueAndGlutenFreeTrue();
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No vegan and gluten-free recipes found.");
		}
		return recipes;
	}

	@GetMapping("/all-recipes")
	public ResponseEntity<List<Recipe>> allRecipes() {
		List<Recipe> recipes = recipeRepository.findAll();
		if (recipes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No recipes found.");
		}
		return new ResponseEntity<>(recipes, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Recipe createRecipe(@RequestBody Recipe recipe) {
		return recipeRepository.save(recipe);
	}

}
