package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class UserOrientationFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private SharedPreferences.Editor editor;
    private View view;

    private String userOrientationForPreferences;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_orientation, container, false);

        initializeButtons();

        return view;
    }

    private void initializeButtons() {

        Button incoming_button = (Button) view.findViewById(R.id.incoming_button);
        Button outgoing_button = (Button) view.findViewById(R.id.outgoing_button);

        incoming_button.setOnClickListener(this);
        outgoing_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == view.findViewById(R.id.incoming_button)) {

            userOrientationForPreferences = "incoming";

            // To not let the Shared Preference stay null:
            setSharedPreferenceData("department", "Not applicable");

            // If the user is Incoming, we have to add a Fragment to the
            // queue, that asks for some specific data:
            ((UserDataInputActivity)getActivity()).addIncomingRelatedFragmentsToQueue();

        } else if (v == view.findViewById(R.id.outgoing_button)) {

            userOrientationForPreferences = "outgoing";

            // We only want to ask about the Department, if a user is a University of Ioannina
            // outgoing Erasmus applicant:
            ((UserDataInputActivity)getActivity()).addDepartmentFragmentToQueue();
        }

        setSharedPreferenceData("userOrientation", userOrientationForPreferences);

        moveToNextFragment();
    }

    private void setSharedPreferenceData(String tag, String data) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Erasmus Prefs", 0);
        editor = sharedPreferences.edit();
        editor.putString(tag, data);
        editor.commit();
    }

    private void moveToNextFragment() {
        ((UserDataInputActivity)getActivity()).goToNextFragment();
    }

}
