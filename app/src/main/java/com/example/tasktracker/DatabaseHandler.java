package com.example.tasktracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskManager";
    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ADDTIME = "addTime";
    private static final String KEY_ADDDATE = "addDate";
    private static final String KEY_DUETIME = "dueTime";
    private static final String KEY_DUEDATE = "dueDate";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_COMPLETION = "completion";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT,"
                + KEY_ADDTIME + " TEXT, " + KEY_ADDDATE + " TEXT, " + KEY_DUETIME + " TEXT,"
                +KEY_DUEDATE + " TEXT, " + KEY_PRIORITY + " TEXT, " + KEY_COMPLETION + " TEXT,"
                + KEY_DESCRIPTION + " TEXT);";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, task.getTitle());
        contentValues.put(KEY_ADDTIME, task.getAddTime());
        contentValues.put(KEY_ADDDATE, task.getAddDate());
        contentValues.put(KEY_DUETIME, task.getDueTime());
        contentValues.put(KEY_DUEDATE, task.getDueDate());
        contentValues.put(KEY_PRIORITY, task.getPriority());
        contentValues.put(KEY_COMPLETION, task.getCompletion());
        contentValues.put(KEY_DESCRIPTION, task.getDescription());

        db.insert(TABLE_TASKS, null, contentValues);
        db.close();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();

        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTitle(cursor.getString(1));
                task.setAddTime(cursor.getString(2));
                task.setAddDate(cursor.getString(3));
                task.setDueTime(cursor.getString(4));
                task.setDueDate(cursor.getString(5));
                task.setPriority(cursor.getString(6));
                task.setCompletion(cursor.getString(7));
                task.setDescription(cursor.getString(8));
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        return tasks;
    }

    Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, null, KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Task task = new Task(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8));
        return task;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " =?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, task.getTitle());
        contentValues.put(KEY_ADDTIME, task.getAddTime());
        contentValues.put(KEY_ADDDATE, task.getAddDate());
        contentValues.put(KEY_DUETIME, task.getDueTime());
        contentValues.put(KEY_DUEDATE, task.getDueDate());
        contentValues.put(KEY_PRIORITY, task.getPriority());
        contentValues.put(KEY_COMPLETION, task.getCompletion());
        contentValues.put(KEY_DESCRIPTION, task.getDescription());

        return db.update(TABLE_TASKS, contentValues, KEY_ID + " =? ",
                new String[] {String.valueOf(task.id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, null,null);
        db.close();
    }
}
