package com.emexezidis.alex.ErasmusUoiApp.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.emexezidis.alex.ErasmusUoiApp.classes.ErasmusProcess;
import com.emexezidis.alex.ErasmusUoiApp.classes.Step;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDataHandler {

    // These are used to remove all the preferences when a reset is requested from the user:
    public static ArrayList<String> preferenceTypes = new ArrayList<>(Arrays.asList("name", "surname", "email", "userOrientation", "erasmusType", "department",
            "country", "nationality", "homeInstitution", "studyField", "studyCycle", "appIsSetUp", "dataIsUploaded"));

    // User-readable names for each of the above preferences. "Empty" is it's not meant to be shown to users:
    public static ArrayList<String> preferenceNames = new ArrayList<>(Arrays.asList("Name", "Surname", "email", "Orientation", "Erasmus Type", "Department",
            "Country", "Nationality", "Home Institution", "Study Field", "Study Cycle", "", ""));

    private Activity callingActivity;

    public UserDataHandler(Activity callingActivity) {
        this.callingActivity = callingActivity;
    }

    public void initializeUserDataFile(String erasmusType, String erasmusOrientation){

        // Here, we want to create the user-requested type of steps, for the first time.
        // Our goal is to take the specific steps from the general-info XML, and create
        // a specific for our user, to be also used as persistent data storage.

        // First of all, we import the requested type of steps from the main XML file:
        XmlStepsImporter xmlImporter = new XmlStepsImporter(callingActivity);
        ErasmusProcess erasmusProcessForInitialization = new ErasmusProcess();

        erasmusProcessForInitialization.attachStepsList(xmlImporter.getProcess(erasmusType, erasmusOrientation));

        // Now, we want to write the 'erasmusProcessForInitialization' step-data, as an
        // XML file, in our app's data storage:
        saveStepsData(erasmusProcessForInitialization);
    }

    public void saveStepsData(ErasmusProcess processDataToSave) {

        String outputString = convertErasmusProcessToXML(processDataToSave);

        String userDataFilename = "userData.xml";

        File path = callingActivity.getFilesDir();
        File file = new File(path, userDataFilename);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream.write(outputString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Step> loadStepsData() {

        XmlStepsImporter stepsImporter = new XmlStepsImporter(callingActivity);
        return(stepsImporter.getUserProcess("userData.xml"));
    }

    private String convertErasmusProcessToXML(ErasmusProcess processDataToSave) {

        int steps = processDataToSave.getNumberOfSteps();
        String outputString = "";

        outputString+="<user>\n";

        for (int i=0; i<steps; i++) {

            outputString+="\n<step ";

            outputString+="group=\""+processDataToSave.getStep(i).getGroup()+"\" ";
            outputString+="name=\""+processDataToSave.getStep(i).getName()+"\" ";
            outputString+="description=\""+processDataToSave.getStep(i).getDescription()+"\" ";
            outputString+="url=\""+processDataToSave.getStep(i).getUrl()+"\" ";
            outputString+="latitude=\""+processDataToSave.getStep(i).getLatitude()+"\" ";
            outputString+="longitude=\""+processDataToSave.getStep(i).getLongitude()+"\" ";
            outputString+="state=\""+processDataToSave.getStep(i).getState()+"\" ";

            outputString+="> ";
            outputString+="\n</step>\n";
        }

        outputString+="</user>";

        return outputString;
    }

    public static void resetData(Context applicationContext) {

        SharedPreferences settings = applicationContext.getSharedPreferences("Erasmus Prefs", 0);
        SharedPreferences.Editor editor = settings.edit();

        for(String type : preferenceTypes) {
            editor.remove(type);
        }

        editor.apply();
    }
}