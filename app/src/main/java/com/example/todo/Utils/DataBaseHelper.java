package com.example.todo.Utils;
//importing database

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final String DATABASE_NAME="TODO_DATABASE";
    private static final String TABLE_NAME="TODO_TABLE";
    private static final String COL_1="ID";
    private static final String COL_2="TASK";
    private static final String COL_3="STATUS";
    private static final String COL_4 = "PRIORITY";
    private static final String COL_5 = "DESCRIPTION";
    private static final String COL_6 = "DUE_DATE";
    private static final String COL_7 = "CATEGORY";


    private static final int DATABASE_VERSION = 7;




    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT , STATUS INTEGER , PRIORITY TEXT DEFAULT 'LOW', DESCRIPTION TEXT DEFAULT '', DUE_DATE TEXT DEFAULT '' , CATEGORY TEXT DEFAULT '')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newer) {
        if(old<7){
            db.execSQL(" ALTER TABLE "+TABLE_NAME+" ADD COLUMN "+COL_7+" TEXT DEFAULT ''");
        }


    }

    public void insertTask(ToDoModel model){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2,model.getTask());
        values.put(COL_3,0);
        values.put(COL_4, model.getPriority());
        values.put(COL_5, model.getDesc());
        values.put(COL_6, model.getDue());
        values.put(COL_7, model.getCategory());
        //insert method
        db.insert(TABLE_NAME,null,values);
    }
    public void updateDueDate(int taskId, String newDueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_6, newDueDate);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(taskId)});
    }

    public void updateTask(int id,String task,String priority,String description,String category){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2,task);
        values.put(COL_4, priority);
        values.put(COL_5, description);
        values.put(COL_7, category);


        //update method
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});


    }
    public void updateStatus(int id,int status){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_3,status);
        //update method
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});


    }

    public void deleteTask(int id){
        db=this.getWritableDatabase();
        //delete
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }


    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){
        db=this.getWritableDatabase();
        Cursor cursor=null;
        List<ToDoModel> modelList=new ArrayList<>();
        db.beginTransaction();
        try{
            cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    do{
                       ToDoModel task=new ToDoModel();
                       task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                       task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                       task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        task.setPriority(cursor.getString(cursor.getColumnIndex(COL_4)));
                        task.setDesc(cursor.getString(cursor.getColumnIndex(COL_5)));
                        task.setDue(cursor.getString(cursor.getColumnIndex(COL_6)));
                        task.setCategory(cursor.getString(cursor.getColumnIndex(COL_7)));

                        modelList.add(task);


                    }while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }


}
