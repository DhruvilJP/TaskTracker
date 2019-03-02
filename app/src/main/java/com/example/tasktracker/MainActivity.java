package com.example.tasktracker;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Task> tasksList = new ArrayList<Task>();
    private ActionBar actionBar;
    private Button btnAddTask;
    private AlertDialog.Builder builder;
    DatabaseHandler db = new DatabaseHandler(this);
    boolean flag = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorBackground)));
        actionBar.setTitle("Task Tracker");

        tasksList = db.getAllTasks();

        final ListView listView = (ListView) findViewById(R.id.mainListView);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setDividerHeight(100);

        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        builder = new AlertDialog.Builder(this);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    public void addTask() {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        startActivity(intent);
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tasksList.size();
        }

        @Override
        public Object getItem(int position) {
            return tasksList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return tasksList.get(position).id;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlistviewlayout, null);

            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            final FrameLayout frameLayout = (FrameLayout) convertView.findViewById(R.id.frameLayout);

            txtTitle.setText(tasksList.get(position).title);
            txtTime.setText("Due on: "+ tasksList.get(position).dueDate);
            progressBar.setMax(100);
            progressBar.setProgress(Integer.parseInt(tasksList.get(position).completion.split("%", 2)[0]));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                    intent.putExtra("id", String.valueOf(tasksList.get(position).id));
                    startActivity(intent);
//                    loadFragment(new ViewTaskFragment());
                }
            });
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    builder.setMessage("Do you want to delete this task?")
                            .setTitle("Delete Task?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.deleteTask(tasksList.get(position).id);
                                    Toast.makeText(getApplicationContext(), "Deleted",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
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
                    return true;
                }
            });
            return convertView;
        }
    }

    public void showStats(MenuItem menuItem) {
        if(flag == false) {
            int counter = 0;
            for (int i=0;i<tasksList.size();i++) {
                if (tasksList.get(i).completion.equals("100%")) {
                    counter ++;
                }
            }
            Bundle bundle = new Bundle();
            String data = String.valueOf(tasksList.size()) + "," + String.valueOf(tasksList.size() - counter);
            bundle.putString("arguments",data);
            ShowStatsFragment temp = new ShowStatsFragment();
            temp.setArguments(bundle);
            loadFragment(temp);
            flag = true;
        }
        else {
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
            frameLayout.setVisibility(View.GONE);
            flag = false;
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}