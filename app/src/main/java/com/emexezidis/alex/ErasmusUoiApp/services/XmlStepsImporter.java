package com.emexezidis.alex.ErasmusUoiApp.services;

import android.app.Activity;
import android.util.Log;

import com.emexezidis.alex.ErasmusUoiApp.classes.Coordinates;
import com.emexezidis.alex.ErasmusUoiApp.classes.Step;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlStepsImporter {

    private Document document;
    private DocumentBuilderFactory documentBuilderFactory;

    private Activity callingActivity;
    private ArrayList<Step> mProcessSteps;
    private Step step;

    private static final String TAG = "XMLStepsImporter";

    public XmlStepsImporter(Activity callingActivity) {
        this.callingActivity = callingActivity;
    }

    public ArrayList<Step> getNull() {

        addStepToProcess("null", "null", "null");
        return mProcessSteps;
    }

    public ArrayList<Step> getUserProcess(String filename) {

        mProcessSteps = new ArrayList<Step>();

        try {
            importXmlAsDocumentFromInternalStorage(filename);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return (getNull());
        } catch (IOException e) {
            e.printStackTrace();
            return (getNull());
        } catch (SAXException e) {
            e.printStackTrace();
            return (getNull());
        }

        parseSteps();

        return mProcessSteps;
    }

    private void importXmlAsDocumentFromInternalStorage(String filename) throws ParserConfigurationException, IOException, SAXException {

        // Here is where the XML-file (from the Internal storage) is loaded as a Document:
        File path = callingActivity.getFilesDir();
        File file = new File(path, filename);
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setCoalescing(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        document = documentBuilder.parse(inputStream);
    }

    public ArrayList<Step> getProcess(String type, String orientation) {

        mProcessSteps = new ArrayList<Step>();

        try {
            if (orientation.equals("outgoing")) {
                importXmlAsDocumentFromAssets("erasmusProcessOutgoing.xml");
                Log.i(TAG, "Importing Outgoing XML File");
            } else {
                importXmlAsDocumentFromAssets("erasmusProcessIncoming.xml");
                Log.i(TAG, "Importing Incoming XML File");
            }
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        if(type.equals("intern")){
            parseSteps("internship");
        } else {
            parseSteps("studying");
        }

        return mProcessSteps;
    }

    private void parseSteps() {

        NodeList steps = document.getElementsByTagName("step");

        for (int i = 0; i < steps.getLength(); i++) {
            getAttributesFromStepNode(steps.item(i));
        }
    }

    private void parseSteps(String type){

        NodeList erasmusTypeNode = document.getElementsByTagName(type);
        Element erasmusTypeElement;
        NodeList steps;

        if(erasmusTypeNode.getLength()>0) {
            erasmusTypeElement = (Element) erasmusTypeNode.item(0);
            steps = erasmusTypeElement.getElementsByTagName("step");

            for (int i = 0; i < steps.getLength(); i++) {
                getAttributesFromStepNode(steps.item(i));
            }
        }
    }

    private void getAttributesFromStepNode(Node currentItem) {

        String groupString = getAttributeValue(currentItem, "group");
        String name = getAttributeValue(currentItem, "name");
        String description = getAttributeValue(currentItem, "description");
        String url = getAttributeValue(currentItem, "url");
        String latitudeString = getAttributeValue(currentItem, "latitude");
        String longitudeString = getAttributeValue(currentItem, "longitude");
        String state = getAttributeValue(currentItem, "state");

        Coordinates coordinates;
        coordinates = parseCoordinates(latitudeString, longitudeString);

        boolean stateBoolean = Boolean.parseBoolean(state);

        addStepToProcess(groupString, name, description, url, coordinates.getLatitude(), coordinates.getLongitude(), stateBoolean);
    }

    private void importXmlAsDocumentFromAssets(String xmlFile) throws ParserConfigurationException, IOException, SAXException {

        // Here is where the XML-file is loaded as a Document:
        InputStream inputStream = callingActivity.getAssets().open(xmlFile);

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setCoalescing(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(inputStream);
    }

    private String getAttributeValue(Node node, String name) {

        if (node.hasAttributes()) {
            if(node.getAttributes().getNamedItem(name) != null) {
                return node.getAttributes().getNamedItem(name).getNodeValue();
            }
        }

        return "";
    }

    private void addStepToProcess(String group, String title, String description, String url, float latitude, float longitude, boolean state) {

        step = new Step(group, title, description);
        step.setUrl(url);
        step.setLatitude(latitude);
        step.setLongitude(longitude);
        step.setState(state);

        mProcessSteps.add(step);
    }

    private Coordinates parseCoordinates(String latitudeString, String longitudeString) {

        float latitude = 0f;
        float longitude = 0f;

        if (latitudeString != null && longitudeString != null) {
            try {
                latitude = Float.parseFloat(latitudeString);
                longitude = Float.parseFloat(longitudeString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                latitude = 0f;
                longitude = 0f;
            }
        }

        return new Coordinates(latitude, longitude);
    }

    private void addStepToProcess(String group, String title, String description) {

        step = new Step(group, title, description);
        mProcessSteps.add(step);
    }
}
