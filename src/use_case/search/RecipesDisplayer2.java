package use_case.search;

import interface_adapter.search_form.SearchFormController;
import org.json.*;
import view.*;

import java.util.*;

public class RecipesDisplayer2 {
    public static void DisplayRecipes() {
        // Retrieve data from the controller
        JSONObject data = SearchFormController.getRecipeData();
        int arr_length = SearchFormController.getArrLength();
        int index = SearchFormController.getIndex();
        String select = SearchFormController.getUserChoice();

        RecipeList recipeList = new RecipeList(null);

        switch (select.toLowerCase()) {
            case "q":
                //findOtherRecipes = false;
                break;
            case "n":
                index = displayRecipeLabels(data, index, arr_length, recipeList);
                SearchFormController.setindex(index);
                break;
            case "p":
                index = index - 20;
                if (index < 0) {
                    index = 0;
                }
                index = displayRecipeLabels(data, index, arr_length, recipeList);
                SearchFormController.setindex(index);
                break;
            default:
                int selection = Integer.parseInt(select);
                if (selection > 0 && selection - 1 < index) {
                    currRecipeCls currRecipe = new currRecipeCls(data, selection - 1);
                    displayRecipeDict(currRecipe, recipeList);
                }
                break;
        }
    }

    public static int displayRecipeLabels(JSONObject data, int index, int arr_length, RecipeList recipeList) {
        //System.out.println();
        HitsCls hits = new HitsCls(data, index, arr_length);
        String[] hitsArray = hits.getHitsArray();
        ArrayList<String> recipes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (hitsArray[i] != null) {
                System.out.println(hitsArray[i]);
                SearchFormController.setRecipeList(hitsArray[i]);
                recipes.add(hitsArray[i]);
                index++;
            }
        }
        //System.out.println();
        // Update the RecipeList GUI with the recipes
        recipeList.updateRecipeList(recipes);
        return index;
    }


    public static void displayRecipeDict(currRecipeCls currRecipe, RecipeList recipeList) {
        ArrayList<String> ingredient = new ArrayList<>();
        ingredient.add("");
        ingredient.add("==========================================================================");
        ingredient.add(currRecipe.getcurrRecipeLabel() + ":");
        ingredient.add("--------------------------------------------------------------------------");
        
        JSONArray ingredientsLine = currRecipe.getingredientsLine();
        for (int i = 0; i < ingredientsLine.length(); i++) {
            ingredient.add(" - " + ingredientsLine.getString(i));
        }
        ingredient.add("");
        ingredient.add("Directions: " + currRecipe.getcurrRecipeUrl());
        ingredient.add("==========================================================================");
        ingredient.add("");
        recipeList.updateRecipeList(ingredient);
    }

}
