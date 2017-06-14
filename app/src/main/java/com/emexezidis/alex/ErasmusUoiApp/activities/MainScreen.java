package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.adapters.CardsAdapter;
import com.emexezidis.alex.ErasmusUoiApp.classes.Card;
import com.emexezidis.alex.ErasmusUoiApp.classes.NextToDoCard;
import com.emexezidis.alex.ErasmusUoiApp.classes.RestoreSettingsItem;
import com.emexezidis.alex.ErasmusUoiApp.classes.TipCard;
import com.emexezidis.alex.ErasmusUoiApp.services.GoogleFormsDataUploader;
import com.emexezidis.alex.ErasmusUoiApp.services.UserDataHandler;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView header_text;
    private TextView logout_header_text;

    private CardsAdapter cardsAdapter;
    private ArrayList<Card> cards;

    private final int SHOW_INFO_SLIDER_ACTIVITY_RESULT = 102;
    private final int ENTER_USER_DATA_ACTIVITY_RESULT = 103;
    private final int START_SETTINGS_FOR_RESULT_CODE = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUiElements();
        beginProgramState();
    }

    private void initializeUiElements() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header_text = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_text);
        logout_header_text = (TextView) navigationView.getHeaderView(0).findViewById(R.id.logout_text);

        logout_header_text.setOnClickListener(this);
    }

    private void beginProgramState() {

        getSharedPreferences();

        if (isAppAlreadySetUpWithUserData()) {

            startCardView();
            manageUserDataUpload();

            manageDatesMenuItemVisibility();

            String name = mSharedPreferences.getString("name", "");
            String surname = mSharedPreferences.getString("surname", "");
            header_text.setText(name + " " + surname);

        } else {

            showInfoSlider();
        }
    }

    private void manageDatesMenuItemVisibility() {
        if (getSharedPreference("userOrientation").equals("incoming")) {
            MenuItem datesItem = navigationView.getMenu().findItem(R.id.dates);
            datesItem.setVisible(true);
        }
    }

    private void manageUserDataUpload() {

        if ((mSharedPreferences.getString("uploadDataUserChoice", "no").equals("yes")))
            if (!mSharedPreferences.getBoolean("dataIsUploaded", false)) {

                // If data has not been already uploaded:
                GoogleFormsDataUploader uploader = new GoogleFormsDataUploader(getApplicationContext());

                String fullName = getSharedPreference("name") + " " + getSharedPreference("surname");
                uploader.setName(fullName);
                uploader.setEmail(getSharedPreference("email"));
                uploader.setErasmus_type(getSharedPreference("erasmusType"));
                uploader.setDepartment(getSharedPreference("department"));
                uploader.setOrientation(getSharedPreference("userOrientation"));
                uploader.setCountry(getSharedPreference("country"));
                uploader.setNationality(getSharedPreference("nationality"));
                uploader.setHomeInstitution(getSharedPreference("homeInstitution"));
                uploader.setStudyField(getSharedPreference("studyField"));
                uploader.setStudyCycle(getSharedPreference("studyCycle"));

                uploader.execute();
            }
    }

    private void getSharedPreferences() {
        mSharedPreferences = getApplicationContext().getSharedPreferences("Erasmus Prefs", 0);
    }

    private boolean isAppAlreadySetUpWithUserData() {
        // Returns the default value (false) if the 'appIsSetUp' preference doesn't exist:
        return mSharedPreferences.getBoolean("appIsSetUp", false);
    }

    private void startCardView() {

        initializeCardViewData();

        cardsAdapter = new CardsAdapter(cards);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardsAdapter);
    }

    private void initializeCardViewData() {

        cards = new ArrayList<Card>();
        cards.add(new NextToDoCard(this));
        cards.add(new TipCard(this));

        if (cardsAdapter != null) {
            updateCardViewData();
        }
    }

    private void updateCardViewData() {

        for (Card card : cards) {
            card.update();
        }

        cardsAdapter.notifyDataSetChanged();
    }

    private void showInfoSlider() {

        if (!isAppAlreadySetUpWithUserData()) {
            startActivityForResult(new Intent(this, ScreenSlidePagerActivity.class), SHOW_INFO_SLIDER_ACTIVITY_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // This is the first thing that happens when a user
        // starts the app for the first time:
        if (requestCode == SHOW_INFO_SLIDER_ACTIVITY_RESULT) {
            setUserData();
        }

        // And this is the second thing:
        if (requestCode == ENTER_USER_DATA_ACTIVITY_RESULT) {

            if (resultCode == RESULT_OK) {

                String name = mSharedPreferences.getString("name", "");
                String surname = mSharedPreferences.getString("surname", "");
                header_text.setText(name + " " + surname);

                // Here we create the specific user's data file by parsing
                // the general XML data file:
                String erasmusType = mSharedPreferences.getString("erasmusType", null);
                String erasmusOrientation = mSharedPreferences.getString("userOrientation", null);

                UserDataHandler userDataHandler = new UserDataHandler(this);
                userDataHandler.initializeUserDataFile(erasmusType, erasmusOrientation);

                // Here we agree that the app has been initialized for the first time:
                setSharedPreferencesBooleanValue("appIsSetUp", true);
                manageUserDataUpload();

                manageDatesMenuItemVisibility();

            } else {

                Toast.makeText(MainScreen.this, R.string.personal_input_data_error, Toast.LENGTH_SHORT).show();
                beginProgramState();
            }
        }

        if (requestCode == START_SETTINGS_FOR_RESULT_CODE) {

            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    private void setUserData() {

        if (!isAppAlreadySetUpWithUserData()) {
            startActivityForResult(new Intent(this, UserDataInputActivity.class), ENTER_USER_DATA_ACTIVITY_RESULT);
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here:
        int id = item.getItemId();

        if (id == R.id.contact) {

            Intent intent = new Intent(this, Contact.class);
            startActivity(intent);

        } else if (id == R.id.faq) {

            Intent intent = new Intent(this, Help.class);
            startActivity(intent);

        } else if (id == R.id.steps_list) {

            openStepsListProcess();

        } else if (id == R.id.campusMap) {

            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);

        } else if (id == R.id.dates) {

            Intent intent = new Intent(this, SemesterDates.class);
            startActivity(intent);

        } else if (id == R.id.settings) {

            Intent intent = new Intent(this, Settings.class);
            startActivityForResult(intent, START_SETTINGS_FOR_RESULT_CODE);

        } else if (id == R.id.about) {

            Intent intent = new Intent(this, AboutScreen.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openStepsListProcess() {

        Intent intent = new Intent(this, StepsListActivity.class);
        startActivity(intent);
    }

    private void setSharedPreferencesBooleanValue(String nameOfPreference, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(nameOfPreference, value);
        editor.apply();
    }

    private String getSharedPreference(String preferenceName) {
        return (mSharedPreferences.getString(preferenceName, ""));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (cardsAdapter != null) {

            updateCardViewData();

        } else {

            if (isAppAlreadySetUpWithUserData()) {
                startCardView();
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.logout_text) {
            new RestoreSettingsItem(this).doAction();
        }
    }
}
