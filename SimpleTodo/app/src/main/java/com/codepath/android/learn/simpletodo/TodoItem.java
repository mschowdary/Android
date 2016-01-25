package com.codepath.android.learn.simpletodo;

import android.text.format.Time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by srinivasam on 1/23/16.
 */
public class TodoItem {
    private int id;
    private String name;
    private Date creationDate;
    private Date dueDate;
    private Boolean started;
    private Boolean completed;

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static int maxid = 1;

    public TodoItem() {
        id = 0;
        this.name = "";
        this.creationDate= new Date();
        this.started = false;
        this.completed = false;
    }

    public TodoItem(String name) {
        this.id = nextMaxId();
        this.name = name;
        this.creationDate= new Date();
        this.started = false;
        this.completed = false;
    }

    public TodoItem(int id, String name, Date creationDate, Date dueDate, Boolean started, Boolean completed) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.started = started;
        this.completed = completed;
    }

    public TodoItem(String name, String creationDate, String dueDate, String started, String completed) {
        this.id = nextMaxId();
        this.name = name;
        try {
            if( creationDate == null || creationDate.isEmpty())
                this.creationDate = null;
            else
                this.creationDate = dateFormat.parse(creationDate);

            if( dueDate == null || dueDate.isEmpty())
                this.dueDate = null;
            else
                this.dueDate = dateFormat.parse(dueDate);

            if( started == null || started.isEmpty())
                this.started = false;
            else
                this.started = Boolean.parseBoolean(started);

            if( completed == null || completed.isEmpty())
                this.completed = false;
            else
                this.completed = Boolean.parseBoolean(completed);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TodoItem(int id, String name, String creationDate, String dueDate, String started, String completed) {
        this.id = id;
        this.name = name;
        try {
            if( creationDate == null || creationDate.isEmpty())
                this.creationDate = null;
            else
                this.creationDate = dateFormat.parse(creationDate);

            if( dueDate == null || dueDate.isEmpty())
                this.dueDate = null;
            else
                this.dueDate = dateFormat.parse(dueDate);

            if( started == null || started.isEmpty())
                this.started = false;
            else
                this.started = Boolean.parseBoolean(started);

            if( completed == null || completed.isEmpty())
                this.completed = false;
            else
                this.completed = Boolean.parseBoolean(completed);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Date getCreationDate() {
        return creationDate;
    }
*/
    public String getCreationDate() {
        if(creationDate == null)
            return  null;

        return dateFormat.format(creationDate);
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDate(String date) {
        try {
            this.creationDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
    public Date getDueDate() {
        return dueDate;
    }
    */

    public String getDueDate() {
        if(dueDate == null)
            return  null;
        return dateFormat.format(dueDate);
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueDate(String date) {
        try {
            this.dueDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public static int getMaxid() {
        return maxid;
    }

    public static void setMaxid(int maxid) {
        TodoItem.maxid = maxid;
    }

    public int nextMaxId() {
        TodoItem.maxid = TodoItem.maxid+1;
        return TodoItem.maxid;
    }
}
