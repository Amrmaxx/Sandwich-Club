package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Main Name
        TextView mainName = findViewById(R.id.mainname_tv);
        mainName.setText(sandwich.getMainName());


        // Origin
        TextView origin = findViewById(R.id.origin_tv);
        origin.setText(sandwich.getPlaceOfOrigin());


        // Also Know
        TextView alsoKnown = findViewById(R.id.also_known_tv);
        TextView alsoKnownlabel = findViewById(R.id.also_know_label_tv);

        // hiding TV if no data exists)
        if (sandwich.getAlsoKnownAs().size() == 0) {
            alsoKnown.setVisibility(View.GONE);
            alsoKnownlabel.setVisibility(View.GONE);
        } else {
            String alsoknownData = "";
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoknownData += sandwich.getAlsoKnownAs().get(i);
                if (i + 1 != sandwich.getAlsoKnownAs().size()) {
                    alsoknownData += "\n";
                }
            }
            alsoKnown.setText(alsoknownData);
        }

        // Ingredients
        String ingredientsData = "";
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            ingredientsData += sandwich.getIngredients().get(i);
            if (i + 1 != sandwich.getIngredients().size()) {
                ingredientsData += "\n";
            }
        }
        TextView ingredients = findViewById(R.id.ingredients_tv);
        ingredients.setText(ingredientsData);

        // Description
        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());


    }
}
