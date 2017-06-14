package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class DepartmentChooserFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private View view;
    private SharedPreferences.Editor editor;
    private Spinner department_spinner;
    private String department = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_department_chooser, container, false);

        initializeSharedPreferencesEditor();
        initializeButtons();

        return view;
    }

    private void initializeButtons() {

        department_spinner = (Spinner) view.findViewById(R.id.department_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.departments_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_spinner.setAdapter(adapter);

        Button department_next = (Button) view.findViewById(R.id.department_selection_next_button);

        department_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == view.findViewById(R.id.department_selection_next_button)) {
            department = department_spinner.getSelectedItem().toString();
        }

        setSharedPreferenceData("department", department);

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
        ((UserDataInputActivity) getActivity()).goToNextFragment();
    }

}
