package app.advanced.mdo.baking.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.advanced.mdo.baking.Models.Recipe;
import app.advanced.mdo.baking.R;
import app.advanced.mdo.baking.UI.RecipeFragment.OnRecipeFragmentInteractionListener;

import java.util.List;

public class RecyclerViewRecipeAdapter extends RecyclerView.Adapter<RecyclerViewRecipeAdapter.ViewHolder> {

    private final List<Recipe> myRecipeList;
    private final OnRecipeFragmentInteractionListener mListener;

    public RecyclerViewRecipeAdapter(List<Recipe> items, RecipeFragment.OnRecipeFragmentInteractionListener listener) {
        myRecipeList = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = myRecipeList.get(position);
        holder.mRecipeName.setText(myRecipeList.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnRecipeFragmentInteraction(holder.mItem);
                }
            }
        });

        // Need to add serving to values/string
        String temp = "Serving: "+ myRecipeList.get(position).getServings();
        holder.tvServing.setText(temp);
    }

    @Override
    public int getItemCount() {
        return myRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mRecipeName;
        final TextView tvServing;
        Recipe mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRecipeName = view.findViewById(R.id.recipe_item_iv);
            tvServing = view.findViewById(R.id.tv_serving);
        }
    }
}
