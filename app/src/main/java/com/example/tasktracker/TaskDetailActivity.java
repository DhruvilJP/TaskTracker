package com.example.tasktracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDetailActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private AlertDialog.Builder builder;
    private Button deleteButton;
    private Button addButton;
    private EditText titleText;
    private EditText dueTimeText;
    private EditText dueDateText;
    private Spinner prioritySpin;
    private Spinner completionSpin;
    private EditText descText;
    String id = null;

    final DatabaseHandler db = new DatabaseHandler(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorBackground)));
        actionBar.setTitle("Task Tracker");

        titleText = (EditText) findViewById(R.id.txtTask);
        dueTimeText = (EditText) findViewById(R.id.txtDueTime);
        dueDateText = (EditText) findViewById(R.id.txtDueDate);
        prioritySpin = (Spinner) findViewById(R.id.spnPriority);
        completionSpin = (Spinner) findViewById(R.id.spnCompletion);
        descText = (EditText) findViewById(R.id.txtDescription);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = null;
            }
            else {
                id = extras.getString("id");
                Task task = db.getTask(Integer.parseInt(id));
                Log.d("Task", task.title);
                titleText.setText(task.title, TextView.BufferType.EDITABLE);
                dueTimeText.setText(task.dueTime, TextView.BufferType.EDITABLE);
                dueDateText.setText(task.dueDate, TextView.BufferType.EDITABLE);
                prioritySpin.setSelection(getIndex(prioritySpin, task.priority));
                completionSpin.setSelection(getIndex(completionSpin, task.completion));
                descText.setText(task.description, TextView.BufferType.EDITABLE);
            }
        }

        addButton = (Button) findViewById(R.id.btnSaveTask);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task task = new Task();
                task.setTitle(titleText.getText().toString());
                task.setAddTime(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
                task.setAddDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
                task.setDueTime(dueTimeText.getText().toString());
                task.setDueDate(dueDateText.getText().toString());
                task.setPriority(prioritySpin.getSelectedItem().toString());
                task.setCompletion(completionSpin.getSelectedItem().toString());
                task.setDescription(descText.getText().toString());

                if(id == null) {
                    db.addTask(task);
                    Toast.makeText(getApplicationContext(), "Task added",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    task.id = Integer.parseInt(id);
                    db.updateTask(task);
                    Toast.makeText(getApplicationContext(), "Task updated",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(TaskDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        builder = new AlertDialog.Builder(this);
    }

    public void deleteItem (MenuItem mi) {
        builder.setMessage("Do you want to delete this task?").setTitle("Delete  Task?");

        builder.setMessage("Do you want to delete this task?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(id != null) {
                            db.deleteTask(Integer.parseInt(id));
                            Toast.makeText(getApplicationContext(), "Deleted",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TaskDetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "cancelled",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Delete Task?");
        alertDialog.show();
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i< spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
