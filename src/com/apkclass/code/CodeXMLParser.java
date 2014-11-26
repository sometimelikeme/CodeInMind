package com.apkclass.code;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 28852028 on 11/24/2014.
 */
public class CodeXMLParser {
    public static void codeParse(Reader reader,ArrayList<AnswerNode> answerBeanList){
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            String name = null;
            AnswerNode answerNode = new AnswerNode();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_DOCUMENT){

                }else if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName() == "node"){
                        answerNode = new AnswerNode();
                        answerNode.setAnswerID(Integer.valueOf(xpp.getAttributeValue(null, "id")));
                    }
                    if(xpp.getName() == "subject") {
                        name = "subject";
                    }else if(xpp.getName() == "answer"){
                        name = "answer";
                    }
                }else if(eventType == XmlPullParser.END_TAG){
                    if(xpp.getName() == "node"){
                        answerBeanList.add(answerNode);
                        answerNode = new AnswerNode();
                    }
                }else if(eventType == XmlPullParser.TEXT) {
                    if(name == "subject"){
                        answerNode.setSubject(xpp.getText());
                        name = null;
                    }else if(name == "answer"){
                        answerNode.addAnswer(xpp.getText());
                        name = null;
                    }
                }
                eventType = xpp.next();
            }

        }catch(XmlPullParserException xmlExc){
            xmlExc.printStackTrace();
        }catch(IOException ioExc){
            ioExc.printStackTrace();
        }
    }

    public static void codeParse(Reader reader,HashMap<String, AnswerNode> answerNodeHashMap){
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            String name = null;
            AnswerNode answerNode = new AnswerNode();
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_DOCUMENT){
                }else if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals("node")){
                        answerNode = new AnswerNode();
                        answerNode.setAnswerID(Integer.valueOf(xpp.getAttributeValue(null, "id")));
                    }else if(xpp.getName().equals("subject")) {
                        name = "subject";
                    }else if(xpp.getName().equals("answer")){
                        name = "answer";
                    }
                }else if(eventType == XmlPullParser.END_TAG){
                    if(xpp.getName().equals("node")){
                        answerNodeHashMap.put(String.valueOf(answerNode.getAnswerID()), answerNode);
                        answerNode = new AnswerNode();
                    }
                }else if(eventType == XmlPullParser.TEXT) {
                    if("subject".equals(name)){
                        answerNode.setSubject(xpp.getText().trim());
                        name = null;
                    }else if("answer".equals(name)){
                        answerNode.addAnswer(xpp.getText().trim());
                        name = null;
                    }
                }
                eventType = xpp.next();
            }

        }catch(XmlPullParserException xmlExc){
            xmlExc.printStackTrace();
        }catch(IOException ioExc){
            ioExc.printStackTrace();
        }
    }
}
