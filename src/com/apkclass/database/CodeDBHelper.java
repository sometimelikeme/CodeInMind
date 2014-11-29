package com.apkclass.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 28852028 on 11/24/2014.
 */
public class CodeDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME 	= "code.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME 	= "answerRecorder";

    public Context context;

    private String answerIDSql = "answerID TEXT NOT NULL";
    private String codeNameSql = "codeName TEXT NOT NULL";
    private String lastUpdateDateSql = "lastUpdateDate TEXT NOT NULL";
    private String memLevelSql = "memLevel TEXT NOT NULL";

    public CodeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableSql = "create table " + TABLE_NAME + " " +
                "( _id  INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,"
                                + answerIDSql + ", "
                                + codeNameSql + ", "
                                + lastUpdateDateSql + ", "
                                + memLevelSql + ")";
        sqLiteDatabase.execSQL(createTableSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

//    public int save(AnswerNode answerNode){
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        String codeName = answerNode.getCodeName();
//        String answerID = String.valueOf(answerNode.getanswerID());
//        String memLevel = String.valueOf(answerNode.getMemLevel());
//
//        String whereCluse = "codeName=? AND answerID=?";
//        String[] whereArgs = new String[]{codeName, answerID};
//        Cursor cursor = db.query(TABLE_NAME, null, whereCluse, whereArgs,null,null,null,null);
//        if(cursor.getCount() == 0){
//            insert(answerNode);
//        }else if(cursor.getCount() == 1){
//            update(codeName, answerID, memLevel);
//        }else{
//            return -1;
//        }
//        return 0;
//    }

//    private void insert(AnswerNode answerNode){
//
//        String codeName = answerNode.getCodeName();
//        String answerID = String.valueOf(answerNode.getanswerID());
//        String memLevel = String.valueOf(answerNode.getMemLevel());
//
//        ContentValues cv = new ContentValues();
//        cv.put("codeName", codeName);
//        cv.put("answerID", answerID);
//        cv.put("memLevel", memLevel);
//        Date date = new Date();
//        cv.put("lastUpdateDate", date.getTime());
//        db.insert(TABLE_NAME, null, cv);
//    }

    public void insert(ContentValues cv){
        SQLiteDatabase db = getWritableDatabase();
        String codeName = (String)cv.get("codeName");
        String answerID = (String)cv.get("answerID");

        String whereCluse = "codeName=? AND answerID=?";
        String[] whereArgs = new String[]{codeName, answerID};
        Cursor cursor = db.query(TABLE_NAME, null, whereCluse, whereArgs,null,null,null,null);
        if(cursor.moveToFirst()){
            return;
        }else {
            Date date = new Date();
            cv.put("lastUpdateDate", date.getTime());
            db.insert(TABLE_NAME, null, cv);
        }
        db.close();
    }

    public void update(ContentValues cv, String whereCluse, String[] whereArgs){
        SQLiteDatabase db = getWritableDatabase();

        Date date = new Date();
        cv.put("lastUpdateDate", date.getTime());

        db.update(TABLE_NAME, cv, whereCluse, whereArgs);
        db.close();
    }


//    private void update(String codeName, String answerID, String memLevel){
//        ContentValues cv = new ContentValues();
//        cv.put("memLevel", memLevel);
//        Date date = new Date();
//        cv.put("lastUpdateDate", date.getTime());
//
//        String whereCluse = "codeName=? AND answerID=?";
//        String[] whereArgs = new String[]{codeName, answerID};
//        db.update(TABLE_NAME, cv, whereCluse, whereArgs);
//    }

    public ArrayList<String> selectByMemLevel(int memLevel){
        SQLiteDatabase db = getReadableDatabase();

        String whereCluse = "memLevel=?";
        String[] whereArgs = new String[]{String.valueOf(memLevel)};

        Cursor cursor = db.query(TABLE_NAME, null, whereCluse, whereArgs,null,null,null,null);

        ArrayList<String> result = new ArrayList<String>();
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                result.add(cursor.getString(cursor.getColumnIndex("answerID")));
            }
        }
        db.close();
        return result;
    }

    public String getOneByMemLevel(String codeName, int memLevel){
        SQLiteDatabase db = getReadableDatabase();

        String whereCluse = "codeName=? AND memLevel=?";
        String[] whereArgs = new String[]{codeName, String.valueOf(memLevel)};

        Cursor cursor = db.query(TABLE_NAME, null, whereCluse, whereArgs,null,null,null,null);

        if(cursor.moveToFirst()) {
            String answerID = cursor.getString(cursor.getColumnIndex("answerID"));
            db.close();
            return answerID;
        }
        db.close();
        return null;
    }

    public int getMemLevel(int answerID){
        SQLiteDatabase db = getReadableDatabase();

        String whereCluse = "answerID=?";
        String[] whereArgs = new String[]{String.valueOf(answerID)};

        Cursor cursor = db.query(TABLE_NAME, null, whereCluse, whereArgs,null,null,null,null);

        if(cursor.moveToFirst()){
            String memLevel = cursor.getString(cursor.getColumnIndex("memLevel"));
            db.close();
            return Integer.parseInt(memLevel);
        }
        db.close();
        return 100;
    }

    public void incMemLevel(int answerID){
        int memLevel = getMemLevel(answerID);
        if(memLevel<12){
            memLevel++;
        }
        updateMemLevel(answerID, memLevel);
    }

    public void incMemLevel(String codeName, int answerID){
        int memLevel = getMemLevel(answerID);
        if(memLevel<12){
            memLevel++;
        }
        updateMemLevel(codeName, answerID, memLevel);
    }

    public void decMemLevel(int answerID){
        int memLevel = getMemLevel(answerID);
        if(memLevel>0){
            memLevel--;
        }
        updateMemLevel(answerID, memLevel);
    }

    public void decMemLevel(String codeName, int answerID){
        int memLevel = getMemLevel(answerID);
        if(memLevel>0){
            memLevel--;
        }
        updateMemLevel(codeName, answerID, memLevel);
    }

    public void topMemLevel(String codeName, int answerID, int memLevel){
        updateMemLevel(codeName, answerID, memLevel);
    }

    private void updateMemLevel(int answerID, int memLevel){
        ContentValues cv = new ContentValues();
        cv.put("memLevel", String.valueOf(memLevel));

        String whereCluse = "answerID=?";
        String[] whereArgs = new String[]{String.valueOf(answerID)};

        update(cv,whereCluse, whereArgs);
    }

    private void updateMemLevel(String codeName, int answerID, int memLevel){
        ContentValues cv = new ContentValues();
        cv.put("memLevel", String.valueOf(memLevel));

        String whereCluse = "codeName=? AND answerID=?";
        String[] whereArgs = new String[]{codeName, String.valueOf(answerID)};

        update(cv,whereCluse, whereArgs);
    }

}
