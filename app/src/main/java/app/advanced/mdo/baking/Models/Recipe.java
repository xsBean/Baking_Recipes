
package app.advanced.mdo.baking.Models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable
{

    private Integer id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<bakingStep> bakingSteps = null;
    private Integer servings;
    private String image;
    public final static Creator<Recipe> CREATOR = new Creator<Recipe>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return (new Recipe[size]);
        }

    }
    ;

    protected Recipe(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (Ingredient.class.getClassLoader()));
        in.readList(this.bakingSteps, (bakingStep.class.getClassLoader()));
        this.servings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Recipe(Integer recipeId, String author, List<Ingredient> ingredientList, List<bakingStep> bakingStepList, Integer serving, String image) {
        this.id = recipeId;
        this.name = author;
        this.ingredients = ingredientList;
        this.bakingSteps = bakingStepList;
        this.servings = serving;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<bakingStep> getBakingSteps() {
        return bakingSteps;
    }

    public void setBakingSteps(List<bakingStep> bakingSteps) {
        this.bakingSteps = bakingSteps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(ingredients);
        dest.writeList(bakingSteps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
