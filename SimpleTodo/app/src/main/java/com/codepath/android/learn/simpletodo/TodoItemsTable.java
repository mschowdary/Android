package com.codepath.android.learn.simpletodo;

/**
 * Created by srinivasam on 1/24/16.
 */
public class TodoItemsTable {
    public static String NAME = "TODO_ITEMS";
    public static String KEY_ID = "ID";
    public static String KEY_ID_TYPE = "INTEGER PRIMARY KEY";
    public static String KEY_NAME = "NAME";
    public static String KEY_NAME_TYPE = "TEXT";
    public static String KEY_CREATION_DATE = "CREATION_DATE";
    public static String KEY_CREATION_DATE_TYPE = "TEXT";
    public static String KEY_DUE_DATE = "DUE_DATE";
    public static String KEY_DUE_DATE_TYPE = "TEXT";
    public static String KEY_STARTED = "STARTED";
    public static String KEY_STARTED_TYPE = "INTEGER";
    public static String KEY_COMPLETED = "COMPLETED";
    public static String KEY_COMPLETED_TYPE = "INTEGER";


    public static final String SQL_CREATE_TABLE =
                            "CREATE TABLE IF NOT EXISTS " + NAME+" ( "
                            + KEY_ID + " " + KEY_ID_TYPE + ","
                            + KEY_NAME + " " + KEY_NAME_TYPE + ","
                            + KEY_CREATION_DATE + " " + KEY_CREATION_DATE_TYPE+ ","
                            + KEY_DUE_DATE + " " + KEY_DUE_DATE_TYPE + ","
                            + KEY_STARTED + " " + KEY_STARTED_TYPE + ","
                            + KEY_COMPLETED + " " +KEY_COMPLETED_TYPE + ")";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS "+NAME;

    public static final String SQL_GET_ALL =
            "SELECT * FROM " + NAME;
}
