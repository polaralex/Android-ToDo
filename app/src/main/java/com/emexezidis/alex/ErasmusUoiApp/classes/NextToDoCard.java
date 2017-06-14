package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.services.UserDataHandler;
import com.emexezidis.alex.ErasmusUoiApp.activities.StepsListActivity;

import java.util.ArrayList;

public class NextToDoCard extends Card {

    public NextToDoCard(AppCompatActivity callingActivity) {

        super(callingActivity);
        setIcon(R.drawable.icon_next);
        setIntentForOnClick(new Intent(callingActivity, StepsListActivity.class));
        update();
    }

    @Override
    public Intent getOnClickAction() {
        return getIntentForOnClick();
    }

    @Override
    public void update() {

        UserDataHandler userDataHandler = new UserDataHandler(getCallingActivity());
        ArrayList<Step> steps = userDataHandler.loadStepsData();

        Step nextStep = getNextStep(steps);

        if (nextStep != null) {
            setTitle(callingActivity.getString(R.string.next_step_card_title));
            setDescription(nextStep.getName());
        } else {
            setTitle(callingActivity.getString(R.string.next_to_do_card_no_more));
            setDescription(callingActivity.getString(R.string.next_to_do_card_all_done));
        }
    }

    private Step getNextStep(ArrayList<Step> steps) {

        for (Step step : steps) {
            if (!step.getState()) {
                return (step);
            }
        }
        return (null);
    }
}
