package com.apkclass.code;

import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<String> getRandomAnswerArrayList(){
        ArrayList<String> randomAnswerArrayList = new ArrayList<String>(answerArrayList);
        for(int i=0; i<4; i++){
            Random r = new Random();
            int random = r.nextInt(10);
            int index =random % (randomAnswerArrayList.size() - 1);
            if(index != (randomAnswerArrayList.size() -1)) {
                String temp = randomAnswerArrayList.get(index);
                randomAnswerArrayList.remove(index);
                randomAnswerArrayList.add(temp);
            }
        }
        return randomAnswerArrayList;
    }

    public String getCorrectAnswer(){
        return answerArrayList.get(0);
    }

    public void addAnswer(String answer){
        this.answerArrayList.add(answer);
    }


}
