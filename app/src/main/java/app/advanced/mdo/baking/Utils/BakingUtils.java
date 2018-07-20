package app.advanced.mdo.baking.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.advanced.mdo.baking.Models.Ingredient;
import app.advanced.mdo.baking.Models.Recipe;
import app.advanced.mdo.baking.Models.bakingStep;


public abstract class BakingUtils {

    private static final String TAG = BakingUtils.class.getSimpleName();
    public static List<Recipe> loadJSONFromAsset(Context context){

        // Read JSON from local file
        List<Recipe> recipes = new ArrayList<>();
        String json = null;
        try{
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
         } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "JSON String is Null.");
            return null;
        }

        // Parsing Json to objects
        try {
            JSONArray recipesJSON = new JSONArray(json);
            if( recipesJSON.length() == 0) return null;
            for(int i = 0; i<recipesJSON.length(); i++){
                JSONObject recipe = recipesJSON.getJSONObject(i);

                JSONArray ingredients = recipe.getJSONArray("ingredients");
                List<Ingredient> ingredientList = new ArrayList<>();
                for(int j = 0; j< ingredients.length(); j++){
                    JSONObject ingredientO = ingredients.getJSONObject(j);
                    Integer quantity = ingredientO.getInt("quantity");
                    String measure = ingredientO.getString("measure");
                    String ingredientStr = ingredientO.getString("ingredient");
                    ingredientList.add(new Ingredient(quantity,measure,ingredientStr));
                }

                JSONArray steps = recipe.getJSONArray("steps");
                List<bakingStep> bakingStepList = new ArrayList<>();
                for(int k = 0; k< steps.length();k++){
                    JSONObject bakingStepOb = steps.getJSONObject(k);
                    Integer stepId = bakingStepOb.getInt("id");
                    String shortDescription = bakingStepOb.getString("shortDescription");
                    String description = bakingStepOb.getString("description");
                    String videoUrl = bakingStepOb.getString("videoURL");
                    String thumbnailURL = bakingStepOb.getString("thumbnailURL");
                    bakingStepList.add(new bakingStep(stepId,shortDescription,description,videoUrl,thumbnailURL));
                }
                Integer recipeId = recipe.getInt("id");
                String author = recipe.getString("name");
                Integer serving = recipe.getInt("servings");
                String image = recipe.getString("image");
                recipes.add(new Recipe(recipeId,author,ingredientList,bakingStepList,serving,image));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }





//    public static ArrayList<Recipe> readingJSON(Context context) throws IOException{
//        JsonReader reader;
//        ArrayList<Recipe> recipes = new ArrayList<>();
//        try {
//            reader = readJSONFile(context);
//            reader.beginArray();
//            while (reader.hasNext()){
////                recipes.add(readEntry(reader).getSampleID());
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return recipes;
//    }
//
//    private static JsonReader readJSONFile(Context context) throws IOException {
//        AssetManager assetManager = context.getAssets();
//        String uri = null;
//
//        try {
//            for (String asset : assetManager.list("")) {
//                if (asset.endsWith(".json")) {
//                    uri = "asset:///" + asset;
//                }
//            }
//        } catch (IOException e) {
//            Toast.makeText(context, R.string.json_list_load_error, Toast.LENGTH_LONG)
//                    .show();
//        }
//        String userAgent = Util.getUserAgent(context, "Baking");
//        DefaultDataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
//        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
//        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);
//
//        JsonReader reader = null;
//        try {
//            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
//        } finally {
//            Util.closeQuietly(dataSource);
//        }
//
//        return reader;
//    }
//    private static Sample readEntry(JsonReader reader) {
//        Integer id = -1;
//        String composer = null;
//        String title = null;
//        String uri = null;
//        String albumArtID = null;
//
//        try {
//            reader.beginObject();
//            while (reader.hasNext()) {
//                String name = reader.nextName();
//                switch (name) {
//                    case "name":
//                        title = reader.nextString();
//                        break;
//                    case "id":
//                        id = reader.nextInt();
//                        break;
//                    case "composer":
//                        composer = reader.nextString();
//                        break;
//                    case "uri":
//                        uri = reader.nextString();
//                        break;
//                    case "albumArtID":
//                        albumArtID = reader.nextString();
//                        break;
//                    default:
//                        break;
//                }
//            }
//            reader.endObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new Sample(id, composer, title, uri, albumArtID);
//    }
}
