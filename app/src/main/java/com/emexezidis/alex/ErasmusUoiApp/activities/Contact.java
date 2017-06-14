package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;

public class Contact extends AppCompatActivity implements View.OnClickListener{

    private TextView visit_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpInterfaceElements();
    }

    private void setUpInterfaceElements() {

        TextView call_us = (TextView) findViewById(R.id.call_us_number);
        TextView mail_us = (TextView) findViewById(R.id.mail_us_content);
        visit_us = (TextView) findViewById(R.id.visit_us_content);
        visit_us.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.visit_us_content) {
            Uri uri = Uri.parse("geo:39.6195696,20.8450881?q=" + Uri.encode("Πρυτανεία"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            this.startActivity(intent);
        }
    }
}
