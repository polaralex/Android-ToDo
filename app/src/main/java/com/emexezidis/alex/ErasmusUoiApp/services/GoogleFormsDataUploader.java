package com.emexezidis.alex.ErasmusUoiApp.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleFormsDataUploader extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "Data Uploader";

    private static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    // URL and Input Elements derived from Google Form URL
    private static final String URL = ""; // USE A VALID GOOGLE FORMS RESPONSE URL TO USE THIS!
    private static final String NAME_KEY = "entry.94559407";
    private static final String ORIENTATION_KEY = "entry.2110098195";
    private static final String ERASMUS_TYPE = "entry.555976952";
    private static final String DEPARTMENT_KEY = "entry.402806408";
    private static final String EMAIL_KEY = "entry.1088237554";
    private static final String COUNTRY_KEY = "entry.445156523";
    private static final String NATIONALITY_KEY = "entry.1933076356";
    private static final String HOME_INSTITUTION_KEY = "entry.1099908353";
    private static final String STUDY_FIELD_KEY = "entry.731359073";
    private static final String STUDY_CYCLE_KEY = "entry.1603269188";

    // String data to be uploaded through HTTP POST:
    private String name;
    private String orientation;
    private String erasmus_type;
    private String department;
    private String email;
    private String country;
    private String nationality;
    private String homeInstitution;
    private String studyField;
    private String studyCycle;

    private String postBody = "";
    private Context context;

    public GoogleFormsDataUploader(Context context) {
        this.context = context;
    }

    private Boolean uploadData() {

        encodeDataBeforeUpload();
        return postDataToGoogleForm();
    }

    private Boolean postDataToGoogleForm() {

        try {
            if (!URL.equals("")) {
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();

                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(URL)
                        .post(body)
                        .build();

                //Send the request
                Response response = client.newCall(request).execute();

                return true;
            }

            System.out.println("No URL used in the Google Forms Data Uploader.");
            return true;

        } catch (IOException exception) {
            return false;
        }
    }

    private boolean encodeDataBeforeUpload() {
        try {
            //all values must be URL encoded to make sure that special characters like & | ",etc.
            //do not cause problems
            postBody = NAME_KEY + "=" + URLEncoder.encode(name, "UTF-8") +
                    "&" + ORIENTATION_KEY + "=" + URLEncoder.encode(orientation, "UTF-8") +
                    "&" + ERASMUS_TYPE + "=" + URLEncoder.encode(erasmus_type, "UTF-8") +
                    "&" + DEPARTMENT_KEY + "=" + URLEncoder.encode(department, "UTF-8") +
                    "&" + EMAIL_KEY + "=" + URLEncoder.encode(email, "UTF-8") +
                    "&" + COUNTRY_KEY + "=" + URLEncoder.encode(country, "UTF-8") +
                    "&" + NATIONALITY_KEY + "=" + URLEncoder.encode(nationality, "UTF-8") +
                    "&" + HOME_INSTITUTION_KEY + "=" + URLEncoder.encode(homeInstitution, "UTF-8") +
                    "&" + STUDY_FIELD_KEY + "=" + URLEncoder.encode(studyField, "UTF-8") +
                    "&" + STUDY_CYCLE_KEY + "=" + URLEncoder.encode(studyCycle, "UTF-8");

            return true;

        } catch (UnsupportedEncodingException ex) {
            return false;
        } catch (NullPointerException ex) {
            Log.e(TAG, "encodeDataBeforeUpload: Null Pointer Exception", ex);
            return false;
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return uploadData();
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result) {
            context.getSharedPreferences("Erasmus Prefs", 0)
                    .edit()
                    .putBoolean("dataIsUploaded", true)
                    .apply();
        }

        if (result) {
            Log.i(TAG, "onPostExecute: Data Uploaded Successfully.");
        } else {
            Log.i(TAG, "onPostExecute: Data could not be uploaded. Possibly connection error.");
        }
    }

    // Setters:
    public void setName(String name) {
        this.name = name;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setErasmus_type(String erasmus_type) {
        this.erasmus_type = erasmus_type;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) { this.country = country; }

    public void setNationality(String nationality) { this.nationality = nationality; }

    public void setHomeInstitution(String homeInstitution) { this.homeInstitution = homeInstitution; }

    public void setStudyField(String studyField) { this.studyField = studyField; }

    public void setStudyCycle(String studyCycle) { this.studyCycle = studyCycle; }
}
