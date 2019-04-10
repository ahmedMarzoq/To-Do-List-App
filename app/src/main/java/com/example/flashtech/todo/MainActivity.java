package com.example.flashtech.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogE.dialogListner {

    private FloatingActionButton inputButton;
    private String task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputButton = findViewById(R.id.floatingActionButton);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        updateUI();

    }

    public void openDialog(){
        DialogE d = new DialogE();
        d.show(getSupportFragmentManager(),"Input");

        }

    @Override
    public void applayTask(String task) {
        this.task = task;
        MyHelper helper = new MyHelper(getApplicationContext(),"tasks",1);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("task",task);
        long result = db.insert("tasks",null,cv);
        if(result != -1)
            Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_SHORT).show();

        updateUI();
    }

    public void updateUI(){
        MyHelper helper = new MyHelper(getApplicationContext(),"tasks",1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tasks",null);
        cursor.moveToFirst();
        ArrayList<String> tasks = new ArrayList<>();
        while(cursor.moveToNext()){
            String task = cursor.getString(cursor.getColumnIndex("task"));
            tasks.add(task);
        }
        RecyclerView rv = findViewById(R.id.recyclerView);
        MyAdapter adapter = new MyAdapter(tasks, R.layout.item_design);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setAdapter(adapter);
        rv.setLayoutManager(llm);
        DividerItemDecoration did = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(did);
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about){
            Intent i = new Intent(getApplicationContext(),About.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}
