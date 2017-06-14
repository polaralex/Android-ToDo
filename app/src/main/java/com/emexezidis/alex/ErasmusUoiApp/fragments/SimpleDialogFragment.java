package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.services.UserDataHandler;

import java.util.ArrayList;

public class SimpleDialogFragment extends DialogFragment {

    private SharedPreferences mSharedPreferences;

    public SimpleDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_simple_dialog, container, false);
        getDialog().setTitle("User Data");

        mSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("Erasmus Prefs", 0);

        TextView userData = (TextView) rootView.findViewById(R.id.user_data_textview);
        populateTextView(userData);

        Button close = (Button) rootView.findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    private void populateTextView(TextView userData) {

        String text = "";

        ArrayList<String> types = UserDataHandler.preferenceTypes;
        ArrayList<String> names = UserDataHandler.preferenceNames;

        for(int i=0; i<types.size(); i++) {
            if (!names.get(i).isEmpty()) {
                text = text.concat(names.get(i)+": " + getSharedPreference(types.get(i)) + "\n");
            }
        }

        userData.setText(text);
    }

    private String getSharedPreference(String preferenceName){
        return (mSharedPreferences.getString(preferenceName, null));
    }

}