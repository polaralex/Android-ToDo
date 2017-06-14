package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.emexezidis.alex.ErasmusUoiApp.R;

import java.util.ArrayList;

public class TipCard extends Card {

    private int tipCounter = 0;
    private ArrayList<String> tips;

    public TipCard(AppCompatActivity callingActivity) {

        super(callingActivity);
        setIcon(R.drawable.info_icon_vector);
        update();
    }

    @Override
    public Intent getOnClickAction() {
        return null;
    }

    @Override
    public void update() {

        if (tips == null) {
            tips = new ArrayList<>();
            tips.add(callingActivity.getString(R.string.tip_1));
            tips.add(callingActivity.getString(R.string.tip_2));
            tips.add(callingActivity.getString(R.string.tip_3));
        }

        if (tipCounter<tips.size()-1) {
            tipCounter++;
        } else {
            tipCounter = 0;
        }

        setTitle("Tip:");
        setDescription(tips.get(tipCounter));
    }
}
