package com.emexezidis.alex.ErasmusUoiApp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.UserDataInputActivity;

public class ConfirmationScreenFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.confirmation_fragment_layout, container, false);
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

        Button done_button = (Button) view.findViewById(R.id.confirmation_done_button);
        done_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == view.findViewById(R.id.confirmation_done_button)) {
            moveToNextFragment();
        }
    }

    private void moveToNextFragment() {
        ((UserDataInputActivity)getActivity()).goToNextFragment();
    }

}
