package com.vodka.sotodo;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

class DTUtils {
    static void showDialog(Context context, final TextView textView) {
        View picker = View.inflate(context, R.layout.datime_picker, null);
        final DatePicker datePicker = picker.findViewById(R.id.date_picker);
        final TimePicker timePicker = picker.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(picker);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int tmp = timePicker.getCurrentMinute();
                String min = "";
                if (tmp < 10) {
                    min = "0" + tmp;
                } else {
                    min = String.valueOf(tmp);
                }
                String dt = datePicker.getYear() + "年" + (datePicker.getMonth()+1) + "月"
                        + datePicker.getDayOfMonth() + "日 " + timePicker.getCurrentHour()
                        + ":" + min;
                textView.setText(dt);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }
}
