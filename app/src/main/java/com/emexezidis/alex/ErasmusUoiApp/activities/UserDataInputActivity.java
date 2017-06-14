package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.fragments.ConfirmationScreenFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.DataUploadApprovalFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.DepartmentChooserFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.EmailInputFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.IncomingUserDetailsFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.NameInputFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.TypeChooserFragment;
import com.emexezidis.alex.ErasmusUoiApp.fragments.UserOrientationFragment;

import java.util.ArrayList;

public class UserDataInputActivity extends AppCompatActivity {

    private android.support.v4.app.FragmentManager fragmentManager;

    private ArrayList<Fragment> fragmentQueue;
    private Integer currentFragmentPosition;

    private boolean departmentFragmentExistence;
    private boolean isIncomingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_input);

        initializeFragmentQueue();

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {

            currentFragmentPosition = 0;
            goToFragment(currentFragmentPosition);

        } else {

            if (savedInstanceState.getBoolean("departmentFragmentExistence")) {
                addDepartmentFragmentToQueue();
            }

            if (savedInstanceState.getBoolean("isIncomingUser")){
                addIncomingRelatedFragmentsToQueue();
            }

            currentFragmentPosition = savedInstanceState.getInt("currentFragmentPosition");
            goToFragment(currentFragmentPosition);
        }
    }

    private void goToFragment(Integer currentFragment) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        Fragment savedFragment = fragmentQueue.get(currentFragment);

        if(savedFragment != null) {
            fragmentTransaction.replace(R.id.fragment_placeholder, savedFragment, currentFragmentPosition.toString());
        } else {
            fragmentTransaction.replace(R.id.fragment_placeholder, fragmentQueue.get(currentFragmentPosition), currentFragmentPosition.toString());
        }

        fragmentTransaction.commit();
    }

    public void goToNextFragment() {

        currentFragmentPosition++;

        if (currentFragmentPosition == fragmentQueue.size()) {
            setUserDataInputAsDone();
        } else {
            goToFragment(currentFragmentPosition);
        }
    }

    public void setUserDataInputAsDone() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentFragmentPosition", currentFragmentPosition);

        // On Destruction of the Activity, we have to know any optional
        // Fragments were added, to re-add them on re-creation:
        outState.putBoolean("departmentFragmentExistence", departmentFragmentExistence);
        outState.putBoolean("isIncomingUser", isIncomingUser);
        super.onSaveInstanceState(outState);
    }

    private void initializeFragmentQueue() {

        if (fragmentQueue == null) {
            fragmentQueue = new ArrayList<>();
            fragmentQueue.add(new UserOrientationFragment());
            fragmentQueue.add(new NameInputFragment());
            fragmentQueue.add(new EmailInputFragment());
            fragmentQueue.add(new TypeChooserFragment());
            fragmentQueue.add(new DataUploadApprovalFragment());
            fragmentQueue.add(new ConfirmationScreenFragment());
        }
    }

    public void addDepartmentFragmentToQueue() {
        departmentFragmentExistence = true;
        fragmentQueue.add(fragmentQueue.size()-2, new DepartmentChooserFragment());
    }

    public void addIncomingRelatedFragmentsToQueue() {
        isIncomingUser = true;
        fragmentQueue.add(fragmentQueue.size()-2, new IncomingUserDetailsFragment());
    }
}
