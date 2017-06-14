package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class NameInputFragment extends Fragment implements View.OnClickListener {

    private EditText nameInput;
    private EditText surnameInput;

    private SharedPreferences.Editor editor;

    public NameInputFragment() {
        // Required empty public constructor
    }

    public static NameInputFragment newInstance() {
        return new NameInputFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_name_input, container, false);

        Button okButton = (Button) view.findViewById(R.id.okButton);

        nameInput = (EditText) view.findViewById(R.id.nameInput);
        surnameInput = (EditText) view.findViewById(R.id.surnameInput);
        nameInput.requestFocus();

        okButton.setOnClickListener(this);

        initializeSharedPreferencesEditor();

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.okButton) {

            String name = nameInput.getText().toString();
            String surname = surnameInput.getText().toString();

            if (!name.matches(".*\\w.*") || !surname.matches(".*\\w.*")) {

                Toast.makeText(super.getContext(), "Please enter a name!", Toast.LENGTH_SHORT).show();

            } else {

                setSharedPreferenceData("name", name);
                setSharedPreferenceData("surname", surname);
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