package com.example.nicholasarduini.multiplechoicetest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Exam {

    private static final String TAG = Exam.class.getSimpleName();

    //XML tags used to define an exam of multiple choice questions.
    public static final String XML_QUESTION_TEXT = "question_text";
    public static final String XML_EMAIL = "email";
    public static final String XML_NAME = "name";
    public static final String XML_STUDENT_NUMBER = "studentNumber";
    public static final String XML_QUESTION = "question";

    private static String email = "";
    private static String name = "";
    private static String studentNumber = "";

    public static String getEmail(){return email;}

    public static ArrayList pullParseFrom(BufferedReader reader){

        ArrayList<Question> questions = new ArrayList<Question>(); //for now

        String curQuestion = "";
        String aAnswer = "";
        String bAnswer = "";
        String cAnswer = "";
        String dAnswer = "";
        String eAnswer = "";

        // Get our factory and create a PullParser
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(reader); // set input file for parser
            int eventType = xpp.getEventType(); // get initial eventType

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // handle the xml tags encountered
                switch (eventType) {
                    case XmlPullParser.START_TAG: //XML opening tags
                        if(xpp.getName().contains(States.STATE_A_ANSWER)){
                            aAnswer = xpp.nextText();
                        } else if(xpp.getName().contains(States.STATE_B_ANSWER)){
                            bAnswer = xpp.nextText();
                        } else if(xpp.getName().contains(States.STATE_C_ANSWER)){
                            cAnswer = xpp.nextText();
                        } else if(xpp.getName().contains(States.STATE_D_ANSWER)){
                            dAnswer = xpp.nextText();
                        } else if(xpp.getName().contains(States.STATE_E_ANSWER)) {
                            eAnswer = xpp.nextText();
                        } else if(xpp.getName().contains(XML_QUESTION_TEXT)){
                            curQuestion = xpp.nextText();
                        } else if(xpp.getName().contains(XML_EMAIL)){
                            email = xpp.nextText();
                        } else if(xpp.getName().contains(XML_NAME)) {
                            name = xpp.nextText();
                        } else if(xpp.getName().contains(XML_STUDENT_NUMBER)) {
                            studentNumber = xpp.nextText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG: //XML closing tags
                        if(xpp.getName().contains(XML_QUESTION)){
                            questions.add(new Question(curQuestion, aAnswer, bAnswer, cAnswer, dAnswer, eAnswer));
                        }
                        break;

                    default:
                        break;
                }
                //iterate
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
