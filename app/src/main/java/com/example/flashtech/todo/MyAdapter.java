package com.example.flashtech.todo;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    ArrayList<String> tasks = new ArrayList<>();
    int itemlayoutId;
    public MyAdapter(ArrayList<String> tasks,int itemlayoutId){
        this.tasks = tasks;
        this.itemlayoutId = itemlayoutId;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemlayoutId,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final String t = tasks.get(i);
        myViewHolder.task.setText(t);
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyHelper helper = new MyHelper(v.getContext(),"tasks",1);
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("tasks","task=?",new String[]{t});
                tasks.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
