package com.codepath.android.learn.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srinivasam on 1/24/16.
 */
public class TodoDataHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TodoList.db";


    public TodoDataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TodoDataHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TodoDataHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TodoItemsTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TodoItemsTable.SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public TodoItem todoItemFromCursor(Cursor cursor) {
        if(cursor == null)
            return null;

        return  new TodoItem(cursor.getInt(0), //id
                cursor.getString(1), //name
                cursor.getString(2), //creation date
                cursor.getString(3), //due date
                (cursor.getInt(4)==0)?"false":"true", //started
                (cursor.getInt(5)==0)?"false":"true"  //completed
        );
    }

    public ContentValues todoItemToValues(TodoItem item) {
        ContentValues values =  new ContentValues();
        values.put(TodoItemsTable.KEY_ID, item.getId());
        values.put(TodoItemsTable.KEY_NAME, item.getName());
        values.put(TodoItemsTable.KEY_CREATION_DATE, item.getCreationDate());
        if(item.getDueDate() != null && item.getDueDate().isEmpty())
            values.put(TodoItemsTable.KEY_DUE_DATE, item.getDueDate());
        values.put(TodoItemsTable.KEY_STARTED, item.getStarted()?1:0);
        values.put(TodoItemsTable.KEY_COMPLETED, item.getCompleted()?1:0);
        //values.put(TodoItemsTable.KEY_START_DATE, item.getStartDate());

        return values;
    }


    public int getMaxTodoId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + TodoItemsTable.KEY_ID + ") FROM " + TodoItemsTable.NAME, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0);
    }
    //Add new Todo Item
    public void addTodoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TodoItemsTable.NAME, null, todoItemToValues(item));
        db.close();
    }

    public TodoItem getTodoItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TodoItemsTable.NAME,
                        new String[]{TodoItemsTable.KEY_ID,
                                    TodoItemsTable.KEY_NAME,
                                    TodoItemsTable.KEY_CREATION_DATE,
                                    TodoItemsTable.KEY_DUE_DATE,
                                    TodoItemsTable.KEY_STARTED,
                                    TodoItemsTable.KEY_COMPLETED},
                        TodoItemsTable.KEY_ID + " =?",
                        new String[] {String.valueOf(id) }, null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        return todoItemFromCursor(cursor);
    }


    public TodoItem getTodoItemByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TodoItemsTable.NAME,
                new String[]{TodoItemsTable.KEY_ID,
                        TodoItemsTable.KEY_NAME,
                        TodoItemsTable.KEY_CREATION_DATE,
                        TodoItemsTable.KEY_DUE_DATE,
                        TodoItemsTable.KEY_STARTED,
                        TodoItemsTable.KEY_COMPLETED},
                TodoItemsTable.KEY_NAME + " =?",
                new String[] {name }, null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        return todoItemFromCursor(cursor);
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> allTodoItems = new ArrayList<TodoItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(TodoItemsTable.SQL_GET_ALL, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                allTodoItems.add(todoItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return allTodoItems;
    }

    public void updateTodoItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = todoItemToValues(item);
        db.update(TodoItemsTable.NAME, values, TodoItemsTable.KEY_ID + " =?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void deleteTodoItem(TodoItem ti) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TodoItemsTable.NAME, TodoItemsTable.KEY_ID +" =?", new String[] {String.valueOf(ti.getId())});
        db.close();
    }
}
