package com.vodka.sotodo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAct extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        Button pomodara = (Button)findViewById(R.id.pomodora);
        Button count = (Button)findViewById(R.id.TimeCount);
        Button manage = (Button)findViewById(R.id.TimeManagement);

        username = "abc";

        pomodara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToPomodara = new Intent(MainAct.this,
                        com.vodka.sotodo.pomodara.class);
                JumpToPomodara.putExtra("username", username);
                startActivity(JumpToPomodara);
            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToTimecount = new Intent(MainAct.this, TimeCount.class);
                JumpToTimecount.putExtra("username", username);
                startActivity(JumpToTimecount);
            }
        });

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToTimemanagement = new Intent(MainAct.this, TimeManagement.class);
                JumpToTimemanagement.putExtra("username", username);
                startActivity(JumpToTimemanagement);
            }
        });
    }
}