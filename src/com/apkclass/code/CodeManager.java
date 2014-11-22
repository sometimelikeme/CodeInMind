package com.apkclass.code;

import android.util.Log;
import android.util.Xml;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 28852028 on 11/21/2014.
 */
public class CodeManager {

    private String codeName;
    private AVUser codeUser;

    public CodeManager(String name, AVUser user){
        codeName = name;
        codeUser = user;
    }

    public void getCodeListFromServerInBackgroud(int pageCount, int pageSkip, final FindCallback findCallback){
        AVQuery<AVObject> query = new AVQuery<AVObject>("Codes");

        if(pageCount > 0) {
            query.setLimit(pageCount);
        }
        if(pageSkip > 0) {
            query.setSkip(pageCount * pageSkip);
        }

        query.findInBackground(findCallback);
    }

    public ArrayList<String> getCodeListFromServer(int pageCount, int pageSkip){
        AVQuery<AVObject> query = new AVQuery<AVObject>("Codes");

        if(pageCount > 0) {
            query.setLimit(pageCount);
        }
        if(pageSkip > 0) {
            query.setSkip(pageCount * pageSkip);
        }
        ArrayList<String> codeList = new ArrayList<String>();
        try{

            List<AVObject> avObjectList = query.find();
            for(int i=0; i<avObjectList.size(); i++){
                codeList.add(avObjectList.get(i).getString("codeName"));
            }
        }catch (AVException e){
            e.printStackTrace();
        }

        return codeList;
    }

    public void getCodeFromServerInBackground(final String codeName, final GetDataCallback getDataCallback){
        AVQuery<AVObject> query = new AVQuery<AVObject>("Codes");
        query.whereEqualTo("codeName", codeName);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e == null){
                    AVFile code = avObject.getAVFile("codeFile");
                    code.getDataInBackground(getDataCallback);
                } else {
                    Log.e("getCodeFromServer", "can't get code : " + codeName);
                    e.printStackTrace();
                }
            }
        });

    }

    public byte[] getCodeFromServer(String codeName){
        byte[] code = null;
        AVQuery<AVObject> query = new AVQuery<AVObject>("Codes");
        query.whereEqualTo("codeName", codeName);
        try {
            AVObject avObject = query.getFirst();
            AVFile codeFile = avObject.getAVFile("codeFile");
            code = codeFile.getData();
        }catch(AVException e){
            e.printStackTrace();
        }
        return code;
    }

    public CodeNode getCodeNode(String name, AVUser user){
        byte[] code = getCodeFromServer(codeName);
        CodeNode cn = new CodeNode(name, user);
        codeParse(new StringReader(new String(code)), cn.getAnswerNodeArrayList());
        return cn;
    }

    private void codeParse(Reader reader,ArrayList<AnswerNode> answerBeanList){
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            String name = null;
            AnswerNode answerNode = new AnswerNode(codeName, codeUser);
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_DOCUMENT){

                }else if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName() == "node"){
                        answerNode = new AnswerNode(codeName, codeUser);
                        answerNode.setAnswerId(Integer.valueOf(xpp.getAttributeValue(null, "id")));
                    }
                    if(xpp.getName() == "subject") {
                        name = "subject";
                    }else if(xpp.getName() == "answer"){
                        name = "answer";
                    }
                }else if(eventType == XmlPullParser.END_TAG){
                    if(xpp.getName() == "node"){
                        answerBeanList.add(answerNode);
                        answerNode = new AnswerNode(codeName, codeUser);
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
}
