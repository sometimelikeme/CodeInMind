package com.apkclass.study;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class AnswerBean implements Serializable{
	
	public Boolean  answer_flag;         //题目答案标记
	public String   answer_content;      //题目答案内容

    private ArrayList<String> answerList;
    private String subject;

	
	public Boolean getAnswer_flag() {
		return answer_flag;
	}
	public void setAnswer_flag(Boolean answer_flag) {
		this.answer_flag = answer_flag;
	}
	public String getAnswer_content() {
		return answer_content;
	}
	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}

    public void AnswerBean(){
        this.answerList = new ArrayList<String>();
        this.subject = null;
    }
	public void AnswerBean(String subject, ArrayList<String> answerList){
        this.subject = subject;
        this.answerList = answerList;
    }

    public ArrayList<String> getAnswerList(){
        return answerList;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void addAnswer(String answer){
        Log.d("addAnswer", answer);
        this.answerList.add(answer);
    }

    public void clean(){
        this.subject = null;
        this.answerList = null;
    }
}
