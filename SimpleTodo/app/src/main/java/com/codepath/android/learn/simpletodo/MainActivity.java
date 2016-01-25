package com.codepath.android.learn.simpletodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> itemsTodo;
    ArrayAdapter<String> itemsAdapterTodo;
    ListView lvItemsTodo;
    EditText etNewTodoItem;
    TodoDataHandler todoDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        todoDbHelper = new TodoDataHandler(this);

        lvItemsTodo = (ListView) findViewById(R.id.lvItemsTodo);
        itemsTodo = new ArrayList<>();

        itemsAdapterTodo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsTodo) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);

                if(todoDbHelper.getTodoItemByName(text.getText().toString()).getCompleted())
                    text.setTextColor(Color.GREEN);
                else {
                    if(todoDbHelper.getTodoItemByName(text.getText().toString()).getStarted())
                        text.setTextColor(Color.BLUE);
                    else
                        text.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        lvItemsTodo.setAdapter(itemsAdapterTodo);
        etNewTodoItem = (EditText) findViewById(R.id.etNewItem);


        populateTodoListView();

        setupListViewListener();
    }

    public void populateTodoListView() {

        itemsTodo.clear();
        for (TodoItem it : todoDbHelper.getAllTodoItems()) {
            itemsTodo.add(it.getName());
        }

        TodoItem.setMaxid(todoDbHelper.getMaxTodoId());

        itemsAdapterTodo.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonAddItem_click(View view) {
        String itemText = etNewTodoItem.getText().toString();
        itemsAdapterTodo.add(itemText);
        todoDbHelper.addTodoItem(new TodoItem(itemText));
        etNewTodoItem.setText("");

    }

    private void setupListViewListener() {
        lvItemsTodo.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String removedItemName = itemsTodo.get(position);
                    itemsTodo.remove(position);
                    TodoItem ti = todoDbHelper.getTodoItemByName(removedItemName);
                    todoDbHelper.deleteTodoItem(ti);
                    itemsAdapterTodo.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), removedItemName + " deleted." , Toast.LENGTH_SHORT).show();
                    return true;
                }
        });

        lvItemsTodo.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemName = itemsTodo.get(position);
                    //TodoItem ti = todoDbHelper.getTodoItemByName(itemName);
                    Bundle todoBundle = new Bundle();
                    todoBundle.putString("tiname", itemName);

                    Intent intent = new Intent(getApplicationContext(),TodoItemDetails.class);
                    intent.putExtras(todoBundle);
                    startActivity(intent);
                    populateTodoListView();
                    return;
                }
            }
        );
    }
}
