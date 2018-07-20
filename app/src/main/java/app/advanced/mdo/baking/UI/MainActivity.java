package app.advanced.mdo.baking.UI;

import android.content.Intent;
import  android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import app.advanced.mdo.baking.Models.Recipe;
import app.advanced.mdo.baking.Models.bakingStep;
import app.advanced.mdo.baking.R;
import app.advanced.mdo.baking.Utils.BakingUtils;

public class MainActivity extends AppCompatActivity implements
        RecipeFragment.OnRecipeFragmentInteractionListener,
        DetailFragment.OnDetailFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Recipe> recipesList;
    private List<bakingStep> bakingStepList;
    private Recipe recipe;

    private static final String BAKING_STEP_LIST ="baking_step";
    private static final String CURRENT_STEP= "current_step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load data from JSON
        recipesList = new ArrayList<>();
        recipesList = BakingUtils.loadJSONFromAsset(this);

        // Create fragment and add fragment to the screen
        RecipeFragment recipeFragment = new RecipeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .add(R.id.main_container_id, recipeFragment);
        fragmentTransaction.commit();
    }

    public List<Recipe> getRecipesList() {
        return recipesList;
    }
    public Recipe getRecipe() {
        return recipe;
    }


    @Override
    public void OnRecipeFragmentInteraction(Recipe recipe) {
        DetailFragment detailFragment = new DetailFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_id, detailFragment);
        fragmentTransaction.commit();
        this.recipe = recipe;
        this.bakingStepList = this.recipe.getBakingSteps();
    }

    @Override
    public void onDetailFragmentInteraction(int currentStep) {

        // send info to step detail activity.
        Intent intent = new Intent(this, StepDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BAKING_STEP_LIST, (ArrayList<bakingStep>) bakingStepList);
        bundle.putInt(CURRENT_STEP, currentStep);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}