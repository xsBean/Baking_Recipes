package app.advanced.mdo.baking.UI;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.advanced.mdo.baking.Models.bakingStep;
import app.advanced.mdo.baking.R;

public class StepDetailActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static final String BAKING_STEP_LIST ="baking_step";
    private static final String CURRENT_STEP= "current_step";
    private List<bakingStep> stepsList;
    private int currentStep;
    private bakingStep step;
    private TextView tvDescription;
    private Button btNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra(BAKING_STEP_LIST)){
            stepsList = intentThatStartedThisActivity.getParcelableArrayListExtra(BAKING_STEP_LIST);
        }
        if(intentThatStartedThisActivity.hasExtra(CURRENT_STEP)){
            currentStep = intentThatStartedThisActivity.getIntExtra(CURRENT_STEP, 0);
        }

        tvDescription = findViewById(R.id.tv_long_description);
        btNextStep = findViewById(R.id.bt_next_step);

        step = stepsList.get(currentStep);
        if(step.getVideoURL() != null){
            Fragment stepFragment = new StepFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.stepFragmentContainer, stepFragment, "ExoPlayerFragment");
            fragmentTransaction.commit();
        }else {
        }

        // Set text and button
        tvDescription.setText(step.getDescription());
        if(currentStep >= stepsList.size() - 1){
            btNextStep.setVisibility(View.GONE);
        }else {
            btNextStep.setText(stepsList.get(currentStep+1).getShortDescription());
            btNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Move to next step
                    currentStep++;
                    step = stepsList.get(currentStep);
                    String uri = step.getVideoURL();
                    // Check fragment
                    Fragment stepFragment;
                    try{
                        stepFragment = fragmentManager.findFragmentByTag("ExoPlayerFragment");
                    }catch (Exception e){
                        stepFragment = null;
                    }

                    if(stepFragment != null){
                        if(uri != null){
                            stepFragment.onDestroy();
                            stepFragment = new StepFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.stepFragmentContainer, stepFragment, "ExoPlayerFragment")
                                    .commit();
                        }else{
                            getSupportFragmentManager().beginTransaction()
                                    .remove(stepFragment)
                                    .commit();
                        }

                    }else{
                        if(uri != null){
                            stepFragment = new StepFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.stepFragmentContainer, stepFragment, "ExoPlayerFragment")
                                    .commit();
                        }
                    }

                    String shortDes = (currentStep + 1 == stepsList.size()) ? null : stepsList.get(currentStep+1).getShortDescription();
                    updateUI(step.getDescription(),shortDes);
                }
            });
        }

    }
    public Uri getVideoUri(){
        return Uri.parse(step.getVideoURL());
    }
    public int getCurrentStep(){return currentStep;}
    public List<bakingStep> getBakingStepList() {return stepsList; }

    public void updateUI(String longDescription, String sortDescription){
        if(longDescription != null) {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(longDescription);
        }
        else tvDescription.setVisibility(View.GONE);
        if(sortDescription != null){
            btNextStep.setVisibility(View.VISIBLE);
            btNextStep.setText(sortDescription);
        }else {
            btNextStep.setVisibility(View.GONE);
        }
    }
}
