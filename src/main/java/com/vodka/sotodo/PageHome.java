package com.vodka.sotodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class PageHome extends Fragment {
    private Context context;

    public PageHome() {}

    PageHome(Context context) {
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_home, container, false);

        final Button to_clock = view.findViewById(R.id.to_clock);

        to_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MainAct.class);
                startActivity(it);
            }
        });

        return view;
    }
}