package com.apkclass.study;

import java.io.Serializable;

public class AnswerBean implements Serializable{
	
	public Boolean  answer_flag;         //题目答案标记
	public String   answer_content;      //题目答案内容
	
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
	@Override
	public String toString() {
		return "AnswerBean [answer_flag=" + answer_flag + ", answer_content="
				+ answer_content + "]";
	}
	
	
	

}
