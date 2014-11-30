package com.apkclass.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.apkclass.database.CodeDBHelper;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 28852028 on 11/21/2014.
 */
public class CodeNode {

    private Context context;
    private CodeDBHelper codeDBHelper;

    private String codeName;
    private Bitmap iconBitmap;
    private String description;

    private ArrayList<AnswerNode> answerNodeArrayList;
    private HashMap<String, AnswerNode> answerNodeHashMap;

    public CodeNode(String name){
        codeName = name;
        answerNodeHashMap = new HashMap<String, AnswerNode>();

        if(ifIconExistLocally()){
            this.iconBitmap = getIconBitmapFromLocal();
        }else{
            this.iconBitmap = getIconBitmapFromServer();
            saveIconLocally(this.iconBitmap);
        }
    }

    public CodeNode(Context context, String name){
        this.context = context;
        codeName = name;
        answerNodeHashMap = new HashMap<String, AnswerNode>();
        if(ifIconExistLocally()){
            this.iconBitmap = getIconBitmapFromLocal();
        }else{
            this.iconBitmap = getIconBitmapFromServer();
            saveIconLocally(this.iconBitmap);
        }
    }

    public ArrayList<AnswerNode> getAnswerNodeArrayList(){
        return answerNodeArrayList;
    }

    public void setAnswerNodeArrayList(ArrayList<AnswerNode> answerNodes){
        answerNodeArrayList = answerNodes;
    }

    public void setAnswerNodeHashMap(HashMap<String, AnswerNode> answerNodeHashMap){
        this.answerNodeHashMap = answerNodeHashMap;
    }

    public HashMap<String, AnswerNode> getAnswerNodeHashMap(){
        return this.answerNodeHashMap;
    }

    public String getCodeName(){
        return this.codeName;
    }

    public void setIcon(Bitmap icon){
        this.iconBitmap = icon;
        saveIconLocally(icon);
    }

    public Bitmap getIcon(){

        return this.iconBitmap;
    }
    public AnswerNode getOneAnswerNode(){
        codeDBHelper = new CodeDBHelper(context);
        String answerID = null;
        if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_MIN)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_0)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_1)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_2)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_3)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_4)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_5)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_6)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_7)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_8)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_9)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_10)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_11)) != null){
            Log.d("getOneAnswerNode", "get answer, ID is :" + answerID);
            return answerNodeHashMap.get(answerID);
        }else if((answerID = codeDBHelper.getOneByMemLevel(codeName, AnswerRecorder.MEM_LEVEL_12)) != null){
            Log.d("getOneAnswerNode", "all answer done");
            AnswerNode doneNode = new AnswerNode();
            doneNode.setAnswerID(2048);//yeah, we love 2048
            return doneNode;
        }
        Log.d("getOneAnswerNode", "no answer get");
        return null;
    }

    public void saveOneAnswerNode(AnswerNode answerNode, boolean result){
        codeDBHelper = new CodeDBHelper(context);
        if(result){
            codeDBHelper.incMemLevel(answerNode.getAnswerID());
        }else{
            codeDBHelper.decMemLevel(answerNode.getAnswerID());
        }
        codeDBHelper.close();

    }

    public void saveOneAnswerNodeAsRemembered(AnswerNode answerNode){
        codeDBHelper = new CodeDBHelper(context);
        codeDBHelper.topMemLevel(codeName, answerNode.getAnswerID(), AnswerRecorder.MEM_LEVEL_MAX);
        codeDBHelper.close();
    }

    public boolean saveOneAnswerNode(AnswerNode answerNode, String result){
        codeDBHelper = new CodeDBHelper(context);
        if(answerNode.getCorrectAnswer().equals(result)){
            codeDBHelper.incMemLevel(answerNode.getAnswerID());
            return true;
        }else{
            codeDBHelper.decMemLevel(answerNode.getAnswerID());
        }
        codeDBHelper.close();
        return false;

    }

    private Bitmap getIconBitmapFromServer(){
        AVObject codeObject;
        AVQuery<AVObject> query = new AVQuery<AVObject>("Codes");
        query.whereEqualTo("codeName", codeName);
        try {
            codeObject = query.getFirst();
            byte[] iconBytes = codeObject.getAVFile("icon").getData();
            iconBitmap = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length);
            return iconBitmap;
        }catch(AVException e){
            e.printStackTrace();
        }
        return null;
    }


    public int getOneAnswerMemLevel(AnswerNode answerNode){
        codeDBHelper = new CodeDBHelper(context);
        int memLevel = codeDBHelper.getMemLevel(answerNode.getAnswerID());
        codeDBHelper.close();
        return  memLevel;
    }

    private void saveIconLocally(Bitmap icon){
        String fileName = codeName + ".png";

        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Bitmap getIconBitmapFromLocal(){
        String fileName = codeName + ".png";
        try{
            FileInputStream fileInputStream = context.openFileInput(fileName);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean ifIconExistLocally(){
        String fileName = codeName + ".png";
        String path = "/data/data/" + context.getPackageName() + "/files";
        File iconFile = new File(path, fileName);
        if(iconFile.exists()) {
            return true;
        }
        return false;
    }


}
