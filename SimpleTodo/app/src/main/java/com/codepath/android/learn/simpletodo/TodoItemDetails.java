package com.codepath.android.learn.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class TodoItemDetails extends AppCompatActivity {

    private EditText etTodoItemName;
    private TextView tvCreationDate;
    private Switch swStarted;
    private Switch swCompleted;

    TodoDataHandler todoDbHelper;
    TodoItem todoItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_item_details);

        etTodoItemName = (EditText) findViewById(R.id.etTodoItemName);
        tvCreationDate = (TextView) findViewById(R.id.tvCreationDate);
        swStarted = (Switch) findViewById(R.id.swStarted);
        swCompleted = (Switch)findViewById(R.id.swCompleted);

        todoDbHelper = new TodoDataHandler(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String value = extras.getString("tiname");
            todoItem = todoDbHelper.getTodoItemByName(value);
            if (todoItem != null && todoItem.getId() > 0) {
                etTodoItemName.setText(todoItem.getName());
                tvCreationDate.setText(todoItem.getCreationDate());
                swStarted.setChecked(todoItem.getStarted());
                swCompleted.setChecked(todoItem.getCompleted());
            }
        }


    }


    public void onSaveDetails_click(View view) {
        todoItem.setName(etTodoItemName.getText().toString());
        todoItem.setStarted(swStarted.isChecked());
        todoItem.setCompleted(swCompleted.isChecked());
        todoDbHelper.updateTodoItem(todoItem);

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
