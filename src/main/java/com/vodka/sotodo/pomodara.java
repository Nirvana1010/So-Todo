package com.vodka.sotodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

import com.vodka.sotodo.pomodaraPrompt.confirmListener;

public class pomodara extends AppCompatActivity implements View.OnClickListener, confirmListener{
    int num = 1;
    CountDownTimer pd_timer;
    CountDownTimer rest_timer;
    TextView pd1;
    TextView rest1;
    TextView pd2;
    TextView rest2;
    TextView pd3;
    TextView rest3;
    TextView pd4;
    String name;
    TextView username;
    boolean dialogConfirm = false;
    String dialogEvent;
    //MyDBOpenHelper dbOpenHelper;
    //SQLiteDatabase db;
    pomodaraPrompt pomodaraPrompt = new pomodaraPrompt();
    Bundle bundle = new Bundle();
    String date;

    @Override
    public void onConfirmComplete(boolean confirm, String event) {
        dialogConfirm = confirm;
        dialogEvent = event;
        //Toast.makeText(this, "确认: " + dialogConfirm + "\n" + dialogEvent,
        //        Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodara);
        pd1 = (TextView)findViewById(R.id.pd_time1);
        Button start = (Button)findViewById(R.id.start);
        rest1 = (TextView)findViewById(R.id.rest_time1);
        pd2 = (TextView)findViewById(R.id.pd_time2);
        rest2 = (TextView)findViewById(R.id.rest_time2);
        pd3 = (TextView)findViewById(R.id.pd_time3);
        rest3 = (TextView)findViewById(R.id.rest_time3);
        pd4 = (TextView)findViewById(R.id.pd_time4);
        Button stop = (Button)findViewById(R.id.stop);

        name = getIntent().getStringExtra("username");
        username = (TextView)findViewById(R.id.username);

        username.setText("username: " + name);

        //dbOpenHelper = new MyDBOpenHelper(this);
        //db = dbOpenHelper.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date = year + "-" + month + "-" + day;

        pd_timer = new CountDownTimer(1000*60*25, 1000) {
            @Override
            public void onTick(long sin) {
                if(rest_timer != null)
                    rest_timer.cancel();
                String mm = new DecimalFormat("00").format((sin/1000) / 60);
                String ss = new DecimalFormat("00").format((sin/1000) % 60);
                String timeFormat = mm + ":" + ss;
                switch(num) {
                    case 1:
                        pd1.setText(timeFormat);
                        break;
                    case 3:
                        pd2.setText(timeFormat);
                        break;
                    case 5:
                        pd3.setText(timeFormat);
                        break;
                    case 7:
                        pd4.setText(timeFormat);
                        break;
                }
                //Toast.makeText(pomodara.this, "" + sin/1000, Toast.LENGTH_SHORT).show();
                //5s后自动关闭对话框
                if((10-(sin/1000) % 60) == 5
                        && pomodaraPrompt != null && pomodaraPrompt.getDialog() != null &&
                        pomodaraPrompt.getDialog().isShowing())
                    pomodaraPrompt.dismiss();
            }

            @Override
            public void onFinish() {
                switch(num) {
                    case 1:
                        pd1.setText("完成！");
                        pd1.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 1);

                        dialogConfirm = false;

                        break;
                    case 3:
                        pd2.setText("完成！");
                        pd2.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 3);

                        break;
                    case 5:
                        pd3.setText("完成！");
                        pd3.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 5);

                        break;
                    case 7:
                        pd4.setText("完成！");
                        pd4.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 7);
                        if(pd_timer != null)
                            pd_timer.cancel();
                        if(rest_timer != null)
                            rest_timer.cancel();
                        pd1.setText("25:00");
                        pd1.setTextColor(Color.rgb(	220,20,60));
                        pd2.setText("25:00");
                        pd2.setTextColor(Color.rgb(	220,20,60));
                        pd3.setText("25:00");
                        pd3.setTextColor(Color.rgb(	220,20,60));
                        pd4.setText("25:00");
                        pd4.setTextColor(Color.rgb(	220,20,60));
                        rest1.setText("05:00");
                        rest1.setTextColor(Color.rgb(220,20,60));
                        rest2.setText("05:00");
                        rest2.setTextColor(Color.rgb(220,20,60));
                        rest3.setText("05:00");
                        rest3.setTextColor(Color.rgb(220,20,60));
                        break;
                }
                bundle.putString("name", name);
                bundle.putString("date", date);
                pomodaraPrompt.setArguments(bundle);
                pomodaraPrompt.show(getSupportFragmentManager(), "message");
                if(num == 1 || num == 3 || num == 5)
                    rest_timer.start();
                num++;
            }
        };

        rest_timer = new CountDownTimer(60*5*1000, 1000) {
            @Override
            public void onTick(long sin) {
                pd_timer.cancel();
                String mm = new DecimalFormat("00").format((sin/1000) / 60);
                String ss = new DecimalFormat("00").format((sin/1000) % 60);
                String timeFormat = mm + ":" + ss;
                switch(num) {
                    case 2:
                        rest1.setText(timeFormat);
                        break;
                    case 4:
                        rest2.setText(timeFormat);
                        break;
                    case 6:
                        rest3.setText(timeFormat);
                        break;
                }
                //5s后自动关闭对话框
                if((10-(sin/1000) % 60) == 5
                        && pomodaraPrompt != null && pomodaraPrompt.getDialog() != null &&
                        pomodaraPrompt.getDialog().isShowing())
                    pomodaraPrompt.dismiss();
            }

            @Override
            public void onFinish() {
                switch(num) {
                    case 2:
                        rest1.setText("完成！");
                        rest1.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 2);
                        break;
                    case 4:
                        rest2.setText("完成！");
                        rest2.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 4);
                        break;
                    case 6:
                        rest3.setText("完成！");
                        rest3.setTextColor(Color.rgb(	60,179,113));
                        bundle.putInt("seq", 6);
                        break;
                }
                bundle.putString("name", name);
                pomodaraPrompt.setArguments(bundle);
                pomodaraPrompt.show(getSupportFragmentManager(), "message");
                pd_timer.start();
                num++;
            }
        };

        start.setOnClickListener(this);

        stop.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("帮助");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder help = new AlertDialog.Builder(pomodara.this);
                help.setTitle("番茄工作法");
                help.setMessage("1.番茄钟是什么？\n" +
                        "番茄钟将帮助你使用番茄工作法提高工作效率。\n" +
                        "2.番茄工作法是什么？\n" +
                        "选择一个待完成的任务，一个番茄时间为25分钟；在一个番茄时间内你需要专注工作，中间不允许做任何" +
                        "与任务无关的事情。直到番茄钟响起后，休息5分钟；然后进入下一个循环。" +
                        "工作4个番茄时间后，你可以多休息一会儿。\n" +
                        "⭐3.注意！" +
                        "番茄工作法的原则是不能中断工作，一旦点击“停止”按钮，你将会失去当次番茄钟的全部数据！");
                help.setPositiveButton("确定", null);
                help.show();
                return true;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.start:
                pd_timer.start();
                break;
            case R.id.stop:
                if(pd_timer != null)
                    pd_timer.cancel();
                if(rest_timer != null)
                    rest_timer.cancel();
                pd1.setText("25:00");
                pd1.setTextColor(Color.rgb(	220,20,60));
                pd2.setText("25:00");
                pd2.setTextColor(Color.rgb(	220,20,60));
                pd3.setText("25:00");
                pd3.setTextColor(Color.rgb(	220,20,60));
                pd4.setText("25:00");
                pd4.setTextColor(Color.rgb(	220,20,60));
                rest1.setText("05:00");
                rest1.setTextColor(Color.rgb(220,20,60));
                rest2.setText("05:00");
                rest2.setTextColor(Color.rgb(220,20,60));
                rest3.setText("05:00");
                rest3.setTextColor(Color.rgb(220,20,60));

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pd_timer != null)
            pd_timer.cancel();
    }

}
