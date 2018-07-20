package app.advanced.mdo.baking.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.advanced.mdo.baking.Models.bakingStep;
import app.advanced.mdo.baking.R;

public class RecyclerViewStepAdapter extends Adapter<RecyclerViewStepAdapter.StepViewHolder> {

    private final List<bakingStep> mySteps;
    private final DetailFragment.OnDetailFragmentInteractionListener myDetailFragmentListener;

    public RecyclerViewStepAdapter(List<bakingStep> mySteps, DetailFragment.OnDetailFragmentInteractionListener myDetailFragmentListener) {
        this.mySteps = mySteps;
        this.myDetailFragmentListener = myDetailFragmentListener;
    }

    //    private final OnRecipeFragmentInteractionListener mListener;
    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, final int position) {
        final bakingStep step = holder.step = mySteps.get(position);
        holder.stepView.setText(step.getShortDescription());
        holder.stepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != myDetailFragmentListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    myDetailFragmentListener.onDetailFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mySteps.size() ;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder{

        private bakingStep step;
        private View view;
        private TextView stepView;

        public StepViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.stepView =view.findViewById(R.id.tv_single_step);
        }
    }

}
