package com.apkclass.code;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.apkclass.database.CodeDBHelper;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 28852028 on 11/21/2014.
 */
public class CodeManager {

    private Context context;

    private String codeName;
    private AVUser codeUser;
    private CodeNode codeNode;

    public CodeManager(Context context, String codeName){
        this.context = context;
        this.codeName = codeName;

        initialize();
    }

    private void initialize(){
        if(codeFileIsExist()){ //codeFile exist in local storage
            String code = readCodeFile();
            codeNode = new CodeNode(context, codeName);
            CodeXMLParser.codeParse(new StringReader(code), codeNode.getAnswerNodeHashMap());
            initializeDB();

        }else{ //get codeFile from server
            byte[] codeBytes = getCodeFromServer(codeName);
            saveFileLocally(codeBytes);
            String code = new String(codeBytes);
            codeNode = new CodeNode(context, codeName);
            CodeXMLParser.codeParse(new StringReader(code), codeNode.getAnswerNodeHashMap());
            initializeDB();
        }
    }

    private boolean codeFileIsExist(){
        String fileName = codeName + ".xml";
        String path = "/data/data/" + context.getPackageName() + "/files";
        File codeFile = new File(path, fileName);
        if(codeFile.exists()) {
            return true;
        }
        return false;
    }

    private String readCodeFile(){
        String fileName = codeName + ".xml";

        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder code = new StringBuilder("");
            while((hasRead = inputStream.read(buffer)) > 0){
                code.append(new String(buffer, 0, hasRead));
            }
            inputStream.close();
            return code.toString();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void saveFileLocally(byte[] bytes){
        String fileName = codeName + ".xml";

        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(bytes);
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initializeDB(){
        CodeDBHelper codeDBHelper = new CodeDBHelper(context);
        Iterator iter = codeNode.getAnswerNodeHashMap().keySet().iterator();
        while(iter.hasNext()){
            String key = (String)iter.next();
            AnswerNode answerNode = codeNode.getAnswerNodeHashMap().get(key);
            ContentValues cv = new ContentValues();
            cv.put("codeName", codeName);
            cv.put("answerID", String.valueOf(answerNode.getAnswerID()));
            cv.put("memLevel", String.valueOf(AnswerRecorder.MEM_LEVEL_INIT));
            codeDBHelper.insert(cv);
        }
        codeDBHelper.close();
    }

    public CodeNode getCodeNode(){
        return this.codeNode;
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
}
