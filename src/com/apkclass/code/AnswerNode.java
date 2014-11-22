package com.apkclass.code;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;

import java.util.ArrayList;

/**
 * Created by 28852028 on 11/21/2014.
 */
public class AnswerNode extends AVObject{

    final static int MEM_LEVEL_INIT = 4;
    final static int MEM_LEVEL_MIN = 0;
    final static int MEM_LEVEL_MAX = 12;
    final static int MEM_LEVEL_0 = 0;
    final static int MEM_LEVEL_1 = 1;
    final static int MEM_LEVEL_2 = 2;
    final static int MEM_LEVEL_3 = 3;
    final static int MEM_LEVEL_4 = 4;
    final static int MEM_LEVEL_5 = 5;
    final static int MEM_LEVEL_6 = 6;
    final static int MEM_LEVEL_7 = 7;
    final static int MEM_LEVEL_8 = 8;
    final static int MEM_LEVEL_9 = 9;
    final static int MEM_LEVEL_10 = 10;
    final static int MEM_LEVEL_11 = 11;
    final static int MEM_LEVEL_12 = 12;

//    private String codeName;
//    private int answerId;
//    private AVUser answerUser;
//    private int memLevel;
    private String subject;
    private ArrayList<String> answerArrayList;

    public AnswerNode(String name, int id, AVUser user){
        super("UserAnswerRecorder");
        answerArrayList = new ArrayList<String>();

        put("codeName", name);
        put("answerId", id);
        put("answerUser", user);
        put("memLevel", MEM_LEVEL_INIT);

    }

    public AnswerNode(String name, AVUser user){
        super("UserAnswerRecorder");
        answerArrayList = new ArrayList<String>();

        put("codeName", name);
        put("answerUser", user);
        put("memLevel", MEM_LEVEL_INIT);
    }

    public AnswerNode(String name, int id, AVUser user, int level){
        super("UserAnswerRecorder");
        answerArrayList = new ArrayList<String>();

        put("codeName", name);
        put("answerId", id);
        put("answerUser", user);
        put("memLevel", level);
    }

    public void incMemLevel(){
        increment("memLevel");
    }

    public void decMemLevel(){
        increment("memLevel", -1);
    }

    public void setAnswerId(int id){
        put("answerId", id);
    }
    public int getAnswerId(){
        return getInt("answerId");
    }

    public int getMemLevel(){
        return getInt("memLevel");
    }

    public String getCodeName(){
        return getString("codeName");
    }

    public AVUser getAnswerUser(){
        return getAVUser("answerUser");
    }

    public void setAsRemembered(){
        put("memLevel", MEM_LEVEL_MAX);
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void addAnswer(String answer){
        answerArrayList.add(answer);
    }

    public ArrayList<String> getAnswerArrayList(){
        return answerArrayList;
    }

    public void save(){
        AVQuery query = new AVQuery("UserAnswerRecorder");
        query.whereEqualTo("answerUser", getAnswerUser());
        query.whereEqualTo("codeName", getCodeName());
        query.whereEqualTo("answerId", getAnswerId());
        AVObject anObject = null;
        try {
           anObject = query.getFirst();
        }catch (AVException e){
            e.printStackTrace();
        }

        if(anObject != null){
            anObject.put("memLevel", getMemLevel());
        }else{
            anObject = new AVObject("UserAnswerRecorder");
            anObject.put("answerUser", getAnswerUser());
            anObject.put("codeName", getCodeName());
            anObject.put("answerId", getAnswerId());
            anObject.put("memLevel", getMemLevel());
        }

        try{
            anObject.save();
        }catch(AVException se){
            se.printStackTrace();
        }
    }

    public void saveInBackground(){
        AVQuery query = new AVQuery("UserAnswerRecorder");
        query.whereEqualTo("answerUser", getAnswerUser());
        query.whereEqualTo("codeName", getCodeName());
        query.whereEqualTo("answerId", getAnswerId());
        AVObject anObject = null;
        query.getFirstInBackground(new GetCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e == null){
                    AnswerNode answerNode = (AnswerNode) avObject;
                    answerNode.put("memLevel", getMemLevel());
                }else if(e.getCode() == AVException.OBJECT_NOT_FOUND){
                    saveNewObjectInBackground();
                }
            }
        });
    }

    private void saveNewObjectInBackground(){
        this.saveInBackground();
    }
}
