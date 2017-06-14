package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class DataUploadApprovalFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private SharedPreferences.Editor editor;
    private View view;

    private CheckBox dataUploadCheckbox;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_data_upload_approval, container, false);

        dataUploadCheckbox = (CheckBox) view.findViewById(R.id.data_upload_checkbox);

        initializeSharedPreferencesEditor();
        initializeButtons();

        return view;
    }

    private void initializeButtons() {

        Button nextButton = (Button) view.findViewById(R.id.dataUploadNextButton);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == view.findViewById(R.id.dataUploadNextButton)) {

            if (dataUploadCheckbox.isChecked()) {

                setSharedPreferenceData("uploadDataUserChoice", "yes");
                moveToNextFragment();

            } else {

                setSharedPreferenceData("uploadDataUserChoice", "no");
                moveToNextFragment();

            }
        }
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