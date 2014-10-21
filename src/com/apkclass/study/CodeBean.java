package com.apkclass.study;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 题目内容Bean
 * @author 姜坤
 *
 */

public class CodeBean implements Serializable{
	private int      			id;          //题目id
	private String   			title;       //题目标题
	private ArrayList<AnswerBean> 	answer_list; //题目答案集合
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public ArrayList<AnswerBean> getAnswer_list() {
		return answer_list;
	}



	public void setAnswer_list(ArrayList<AnswerBean> answer_list) {
		this.answer_list = answer_list;
	}

	@Override
	public String toString() {
		return "CodeBean [id=" + id + ", title=" + title + ", answer_list="
				+ answer_list + "]";
	}
	
}
