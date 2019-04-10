package com.example.flashtech.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView task ;
    Button delete;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        task = itemView.findViewById(R.id.textView);
        delete = itemView.findViewById(R.id.button);
    }
}
