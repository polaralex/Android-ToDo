package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class IncomingUserDetailsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private View view;
    private SharedPreferences.Editor editor;

    private AppCompatEditText countryTextField;
    private AppCompatEditText nationalityTextField;
    private AppCompatEditText homeInstitutionTextField;
    private AppCompatEditText studyFieldTextField;
    private Spinner studyCycleSpinner;

    private String country = "";
    private String nationality = "";
    private String homeInstitution = "";
    private String studyField = "";
    private String studyCycle = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_incoming_user_details, container, false);

        initializeButtons();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // This closes the Soft Keyboard (if it's open):
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeButtons() {

        studyCycleSpinner = (Spinner) view.findViewById(R.id.study_cycle_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.study_cycles_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studyCycleSpinner.setAdapter(adapter);

        countryTextField = (AppCompatEditText) view.findViewById(R.id.text_input_country);
        nationalityTextField = (AppCompatEditText) view.findViewById(R.id.text_input_nationality);
        homeInstitutionTextField = (AppCompatEditText) view.findViewById(R.id.text_input_home_institution);
        studyFieldTextField = (AppCompatEditText) view.findViewById(R.id.text_input_study_field);

        Button department_next = (Button) view.findViewById(R.id.incoming_user_details_next_button);
        department_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        initializeSharedPreferencesEditor();

        if (v == view.findViewById(R.id.incoming_user_details_next_button)) {
            country = countryTextField.getText().toString();
            nationality = nationalityTextField.getText().toString();
            homeInstitution = homeInstitutionTextField.getText().toString();
            studyField = studyFieldTextField.getText().toString();
            studyCycle = studyCycleSpinner.getSelectedItem().toString();

            setSharedPreferenceData("country", country);
            setSharedPreferenceData("nationality", nationality);
            setSharedPreferenceData("homeInstitution", homeInstitution);
            setSharedPreferenceData("studyField", studyField);
            setSharedPreferenceData("studyCycle", studyCycle);

            moveToNextFragment();
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
        ((UserDataInputActivity) getActivity()).goToNextFragment();
    }
}
