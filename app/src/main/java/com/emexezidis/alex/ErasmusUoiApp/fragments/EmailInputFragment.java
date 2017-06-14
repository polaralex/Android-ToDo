package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class EmailInputFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private SharedPreferences.Editor editor;
    private View view;

    private EditText emailInput;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_email_input, container, false);

        emailInput = (EditText) view.findViewById(R.id.emailInput);

        initializeSharedPreferencesEditor();
        initializeButtons();

        return view;
    }

    private void initializeButtons() {

        Button nextButton = (Button) view.findViewById(R.id.profileDataInput_nextButton);
        nextButton.setOnClickListener(this);
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onClick(View v) {

        if (v == view.findViewById(R.id.profileDataInput_nextButton)) {

            if (isValidEmail(emailInput.getText().toString())) {

                String email = emailInput.getText().toString();
                setSharedPreferenceData("email", email);

                moveToNextFragment();

            } else {

                Toast.makeText(super.getContext(), "Please enter a correct email!", Toast.LENGTH_SHORT).show();

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
