package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.emexezidis.alex.ErasmusUoiApp.classes.ErasmusProcess;
import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.Step;
import com.emexezidis.alex.ErasmusUoiApp.services.UserDataHandler;
import com.emexezidis.alex.ErasmusUoiApp.adapters.ErasmusStepsRecyclerViewAdapter;
import com.emexezidis.alex.ErasmusUoiApp.adapters.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class StepsListActivity extends AppCompatActivity {

    private ErasmusProcess mErasmusProcess;
    private Intent intent;

    private ErasmusStepsRecyclerViewAdapter recyclerViewAdapter;

    private int ACTIVITY_INFO_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {

            populateListWithProcess();

        } else {
            Bundle savedInstanceBundle = savedInstanceState.getBundle("erasmusProcessBundle");
            mErasmusProcess = savedInstanceBundle.getParcelable("erasmusList");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erasmus_steps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // List View attachment with a process:
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.processRecycleView);

        recyclerViewAdapter = new ErasmusStepsRecyclerViewAdapter(mErasmusProcess.getSteps());
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerViewAdapter.setOnItemClickListener(new ErasmusStepsRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                intent = new Intent(getApplicationContext(), StepInfoScreen.class);
                intent.putExtra("step", recyclerViewAdapter.getItem(position));
                intent.putExtra("stepPosition", position);
                startActivityForResult(intent, ACTIVITY_INFO_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == ACTIVITY_INFO_REQUEST_CODE){

            if(resultCode == RESULT_OK) {

                Step changedStep = data.getExtras().getParcelable("returnedStep");
                int stepPosition = data.getExtras().getInt("stepPosition");
                mErasmusProcess.setExactStep(changedStep, stepPosition);

                recyclerViewAdapter.notifyItemChanged(stepPosition);

                saveData();
            }
        }
    }

    private void populateListWithProcess() {

        UserDataHandler userDataHandler = new UserDataHandler(this);
        ArrayList<Step> processSteps = userDataHandler.loadStepsData();
        mErasmusProcess = new ErasmusProcess(processSteps);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    protected void saveData() {
        UserDataHandler dataHandler = new UserDataHandler(this);
        dataHandler.saveStepsData(mErasmusProcess);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        Bundle erasmusProcessToSave = new Bundle();
        erasmusProcessToSave.putParcelable("erasmusList", mErasmusProcess);
        outState.putBundle("erasmusProcessBundle", erasmusProcessToSave);

        super.onSaveInstanceState(outState);
    }

    // This is used to provide back navigation through the toolbar
    // for 4.0 Android devices:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
