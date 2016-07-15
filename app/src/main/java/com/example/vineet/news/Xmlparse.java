package com.example.vineet.news;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

public class Xmlparse {
   private String xmldata;
    private ArrayList<Application> applications ;


    public Xmlparse (String xmldata) {
        this.xmldata=xmldata;
        applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process () {
        boolean status = true;
        String TextValue="";
        Application currentRecord = null;
        boolean inEntry = false;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmldata));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                   //     Log.d("Xmlparse" ,"Starting tag" +tagName);
                        if(tagName.equalsIgnoreCase("item")) {
                            inEntry=true;
                            currentRecord = new Application();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        TextValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                    //    Log.d("Xmlparse" , " Ending tag" +tagName);
                        if(inEntry) {
                            if(tagName.equalsIgnoreCase("item")) {
                                applications.add(currentRecord);
                                inEntry =false;
                            }
                            else if (tagName.equalsIgnoreCase("title")) {
                                currentRecord.setTitle(TextValue);
                            }
//                            else if (tagName.equalsIgnoreCase("description")) {
//                                currentRecord.setDescription(TextValue);
//                            }
//                            else if (tagName.equalsIgnoreCase("link")) {
//                                currentRecord.setLink(TextValue);
//                            }
                            else if (tagName.equalsIgnoreCase("pubdate")) {
                                currentRecord.setDate(TextValue);
                            }
                        }
                        break;
                    default:
                        //nothing
                }
                eventType = xpp.next();
            }


        }
        catch (Exception e ) {
            e.printStackTrace();
        }
        for (Application app :applications){
            Log.d("Xmlparse","*********************************");
            Log.d("Xmlparse" , " Name: " + app.getTitle());
//            Log.d("Xmlparse" , " Description: " + app.getDescription());
//            Log.d("Xmlparse" , " Link: " + app.getLink());
            Log.d("Xmlparse" , " Date: " + app.getDate());
        }
        return  true;

    }

}
