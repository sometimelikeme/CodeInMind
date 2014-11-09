package com.apkclass.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.apkclass.study.AnswerBean;
import com.apkclass.study.CodeBean;
import com.apkclass.utils.log;

public class XmlParser {
	public static ArrayList<CodeBean> readXML(InputStream inStream) {
		XmlPullParser parser = Xml.newPullParser();
		AnswerBean answerBean = null;
		CodeBean codeBean = null;
		ArrayList<CodeBean> codeBeanList = null;
        ArrayList<AnswerBean> answerList = new ArrayList<AnswerBean>();
		try {
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();
			log.e("eventType:"+eventType);
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
                    log.i("START_DOCUMENT");
					codeBeanList = new ArrayList<CodeBean>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = parser.getName();
                    log.i("START_TAG, TAG name:" + name);
					if(name.equalsIgnoreCase("node")) {
						codeBean = new CodeBean();
                        codeBean.setAnswer_list(answerList);
                            if(codeBean.getAnswer_list() == null){
                                log.i("answerlist alloc failed!!!");
                            }
						codeBean.setId(parser.getAttributeValue(null, "id"));
						log.e(" "+ codeBean.getId());
					}else if(codeBean != null) {
						if(name.equalsIgnoreCase("subject")) {
							codeBean.setTitle(parser.nextText());
							log.e(" "+codeBean.getTitle());
						} else if (name.equalsIgnoreCase("answer")) {
							answerBean = new AnswerBean();
							if(parser.getAttributeValue(null, "correct").equals("Y")) {
								answerBean.setAnswer_flag(true);
								log.e(" "+answerBean.getAnswer_flag());
							}else {
								answerBean.setAnswer_flag(false);
								log.e(" "+answerBean.getAnswer_flag());
							}

                            String content = parser.nextText();
							answerBean.setAnswer_content(content);
                            log.i("set answer content");
                            if(codeBean.getAnswer_list() != null) {
                                codeBean.getAnswer_list().add(answerBean);
                                log.i("Add answer:" + content);
                            } else {
                                log.i("get answer list failed 2");
                            }
						}
					}
					
					break;

//                case XmlPullParser.TEXT:
//                    log.i("TEXT_TAG :" + parser.getText());
//                    break;
					
				case XmlPullParser.END_TAG:
					log.i("END_TAG");
					if(parser.getName().equalsIgnoreCase("node") && codeBean != null) {
						codeBeanList.add(codeBean);
						log.e(" "+codeBean.toString());
						codeBean = null;
					}
					
					if(parser.getName().equalsIgnoreCase("answer")) {
						log.i("END_TAG, TAG name: answer");
					}
					
					break;
				
				default:
					break;
				}
				log.i("parser next");
				eventType = parser.next();
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return codeBeanList;
	}
}
