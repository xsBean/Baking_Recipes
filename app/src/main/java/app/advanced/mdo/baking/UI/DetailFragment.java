package app.advanced.mdo.baking.UI;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import app.advanced.mdo.baking.Models.Ingredient;
import app.advanced.mdo.baking.Models.Recipe;
import app.advanced.mdo.baking.Models.bakingStep;
import app.advanced.mdo.baking.R;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link DetailFragment.OnDetailFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link DetailFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DetailFragment extends Fragment {

    private OnDetailFragmentInteractionListener mListener;

    private Recipe recipe;
    private List<Ingredient> ingredientList;
    private List<bakingStep> bakingStepList;
    private OnDetailFragmentInteractionListener detailFragmentInteractionListener;
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        recipe = activity.getRecipe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Context context = view.getContext();
        ingredientList = recipe.getIngredients();
        bakingStepList = recipe.getBakingSteps();

        RecyclerView rvIngredients = view.findViewById(R.id.rv_ingredients);
        RecyclerView rvBakingSteps = view.findViewById(R.id.rv_steps);

        rvIngredients.setLayoutManager(new LinearLayoutManager(context));
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setAdapter(new RecyclerViewIngredientAdapter(ingredientList));

        rvBakingSteps.setLayoutManager(new LinearLayoutManager(context));
        rvBakingSteps.setHasFixedSize(true);
        rvBakingSteps.setAdapter(new RecyclerViewStepAdapter(bakingStepList, detailFragmentInteractionListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            detailFragmentInteractionListener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDetailFragmentInteraction(int currentStep);
    }
}
