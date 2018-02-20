package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static Sandwich mSandwich = new Sandwich();

    public static Sandwich parseSandwichJson(String json) {


        try {
            //  Parsing Sandwich Data
            JSONObject baseSandwich = new JSONObject(json);
            JSONObject name = baseSandwich.getJSONObject("name");

            //  Main name
            mSandwich.setMainName(name.getString("mainName"));

            // Origin
            mSandwich.setPlaceOfOrigin(baseSandwich.getString("placeOfOrigin"));

            // Also Known
            JSONArray alsoKonwn = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownNames = new ArrayList<>();
            for (int i = 0; i < alsoKonwn.length(); i++) {
                alsoKnownNames.add(alsoKonwn.getString(i));
            }
            mSandwich.setAlsoKnownAs(alsoKnownNames);

            // Ingredients
            JSONArray ingredients = baseSandwich.getJSONArray("ingredients");
            List<String> ingredientsData = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsData.add(ingredients.getString(i));
            }
            mSandwich.setIngredients(ingredientsData);

            // Description
            mSandwich.setDescription(baseSandwich.getString("description"));

            // Image
            mSandwich.setImage(baseSandwich.getString("image"));


        } catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing", e);

        }
        return mSandwich;
    }


}
