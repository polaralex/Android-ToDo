package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class TypeChooserFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private View view;
    private SharedPreferences.Editor editor;

    private String userType = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_type_chooser, container, false);

        initializeSharedPreferencesEditor();
        initializeButtons();

        return view;
    }

    private void initializeButtons() {

        Button student_button = (Button) view.findViewById(R.id.student_button);
        Button intern_button = (Button) view.findViewById(R.id.intern_button);

        student_button.setOnClickListener(this);
        intern_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == view.findViewById(R.id.student_button)) {
            userType = "student";
        } else if (v == view.findViewById(R.id.intern_button)) {
            userType = "intern";
        }

        setSharedPreferenceData("erasmusType", userType);

        moveToNextFragment();
    }

    private void initializeSharedPreferencesEditor() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Erasmus Prefs", 0);
        editor = sharedPreferences.edit();
    }

    private void setSharedPreferenceData(String tag, String data) {
        editor.putString(tag, data);
        editor.commit();
    }

    private void moveToNextFragment() {
        ((UserDataInputActivity)getActivity()).goToNextFragment();
    }

}
