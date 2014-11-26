package com.apkclass.code;

import java.util.ArrayList;

/**
 * Created by 28852028 on 11/21/2014.
 */
public class AnswerNode{

    private String subject;
    private int answerID;
    private ArrayList<String> answerArrayList;

    public AnswerNode(){
        answerArrayList = new ArrayList<String>();
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return this.subject;
    }
    public void setAnswerID(int ID){
        this.answerID = ID;
    }

    public int getAnswerID(){
        return this.answerID;
    }

    public void setAnswerArrayList(ArrayList<String> answerArrayList){
        this.answerArrayList = answerArrayList;
    }

    public ArrayList<String> getAnswerArrayList(){
        return this.answerArrayList;
    }

    public void addAnswer(String answer){
        this.answerArrayList.add(answer);
    }
}
