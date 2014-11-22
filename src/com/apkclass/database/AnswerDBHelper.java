package com.apkclass.database;

import java.util.ArrayList; 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.drm.DrmStore.RightsStatus;

/**
 * 数据库存储类，存储对象为题目id
 * @author 姜坤
 *
 */

public class AnswerDBHelper extends SQLiteOpenHelper {
	
	public static final String TAG  			= "AnswerDBHelper";
	public static final String DATABASE_NAME 	= "answer.db";
	public static final String TABLE_NAME_1 	= "rightanswer";
	public static final String TABLE_NAME_2		= "wronganswer";
	
	public static final int DATABASE_VERSION = 2;
	public Context context;

	public AnswerDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub	
		String sql_1="create table "+TABLE_NAME_1+" ( _id  INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT," + "id TEXT NOT NULL)";
		db.execSQL(sql_1);
		String sql_2="create table "+TABLE_NAME_2+" ( _id  INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT," + "id TEXT NOT NULL)";
		db.execSQL(sql_2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
		onCreate(db);  
	}
	
	/**
	 * 添加id
	 * @param id 	题目id
	 * @param rw	正确答案right 错误答案wrong
	 * @return	
	 */
	public long insert_id(String id, String rw) {  
		SQLiteDatabase db = getWritableDatabase();// 获取可写SQLiteDatabase对象  
		// ContentValues类似map，存入的是键值对 
		if(rw == null) {
			return -1;
		}
		ContentValues contentValues = new ContentValues();  
		contentValues.put("id", id);  
		if(rw.equals("right")) {
			return db.insert(TABLE_NAME_1, null, contentValues);
		}else if(rw.equals("wrong")) {
			return db.insert(TABLE_NAME_2, null, contentValues);
		}else {
			return -1;
		}
	}
	
	
	/**
	 * 删除id		
	 * @param id	题目id
	 * @param rw	正确答案right 错误答案wrong
	 */
	public void delete (String id, String rw) {
		if(rw == null) {
			return;
		}
		SQLiteDatabase db = getWritableDatabase();
		if(rw.equals("right")) {
			db.delete(TABLE_NAME_1, "id=?", new String[] { id });
		}else if(rw.equals("wrong")) {
			db.delete(TABLE_NAME_2, "id=?", new String[] { id });
		}
	}
	
	public ArrayList<String> getidlist(String rw) {
		if(rw == null) {
			return null;
		}
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor=null;
		if(rw.equals("right")) {
			try {
				cursor = db.query(TABLE_NAME_1, new String[] { "id" }, null, null, null, null, "id asc"); // desc 降序排列,asc 升序排列  
				ArrayList<String> rightlist = new ArrayList<String>();
				while(cursor.moveToNext()) {
					rightlist.add(cursor.getString(cursor.getColumnIndex("id")));
				}
				return rightlist;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(rw.equals("wrong")) {
			cursor = db.query(TABLE_NAME_2, new String[] { "id" }, null, null, null, null, "id asc"); // desc 降序排列,asc 升序排列 
			ArrayList<String> wronglist = new ArrayList<String>();
			while(cursor.moveToNext()) {
				wronglist.add(cursor.getString(cursor.getColumnIndex("id")));
			}
			return wronglist;
		} 
		return null;
	}

}
