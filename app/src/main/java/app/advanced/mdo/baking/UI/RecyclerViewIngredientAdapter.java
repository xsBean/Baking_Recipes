package app.advanced.mdo.baking.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.advanced.mdo.baking.Models.Ingredient;
import app.advanced.mdo.baking.R;

public class RecyclerViewIngredientAdapter extends RecyclerView.Adapter<RecyclerViewIngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public RecyclerViewIngredientAdapter(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
            holder.ingredientView.setText(ingredientList.get(position).getIngredient());
            String temp = ingredientList.get(position).getQuantity() + ingredientList.get(position).getMeasure();
            holder.quantityAndMeasureView.setText(temp);
    }
    @Override
    public int getItemCount() {

        return ingredientList.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder{

        public  final View view;
        public final TextView ingredientView;
        public final TextView quantityAndMeasureView;
        public IngredientViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.ingredientView =view.findViewById(R.id.tv_single_ingredient);
            this.quantityAndMeasureView = view.findViewById(R.id.tv_quantity_measure);
        }
    }
}

