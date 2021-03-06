package com.example.taskmanager.models.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskBaseHelper extends SQLiteOpenHelper {
    public TaskBaseHelper(@Nullable Context context) {
        super(context, TaskDbSchema.NAME, null, TaskDbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskDbSchema.Account.NAME+ " ( " +
                "_id integer primary key autoincrement, "
                + TaskDbSchema.Account.AccountCols.UUID + ", "
                + TaskDbSchema.Account.AccountCols.USERNAME + ", "
                + TaskDbSchema.Account.AccountCols.PASSWORD + " ) "
        );
        db.execSQL("Create table " + TaskDbSchema.Task.NAME + " ( " +
        "_id integer primary key autoincrement, "
        + TaskDbSchema.Task.TaskCols.UUID + ", "
        + TaskDbSchema.Task.TaskCols.TITLE + ", "
        + TaskDbSchema.Task.TaskCols.DESCRIPTION + ", "
        + TaskDbSchema.Task.TaskCols.DATE + ", "
        + TaskDbSchema.Task.TaskCols.ISDONE+", "
        + TaskDbSchema.Task.TaskCols.ACCOUNTID +", "
        +"foreign key ( "+ TaskDbSchema.Task.TaskCols.ACCOUNTID+" ) references "
        + TaskDbSchema.Account.NAME + " ( " + "_id" + " ) "
        + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
