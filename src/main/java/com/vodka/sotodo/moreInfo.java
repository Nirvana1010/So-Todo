package com.vodka.sotodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class moreInfo extends Activity {
    String TB_NAME;
    int mID;
    Button btn_dt;
    TextView datime;
    Button OK;
    Button RET;
    EditText title;
    EditText remark;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        btn_dt = findViewById(R.id.btn_dateTime);
        title = findViewById(R.id.titl);
        datime = findViewById(R.id.datime);
        remark = findViewById(R.id.remark);
        OK = findViewById(R.id.OK);
        RET = findViewById(R.id.RET);

        mID = -1;
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b!=null) {
            int op = b.getInt("Option", -1);
            TB_NAME = b.getString("TB_NAME", null);
            if (op==0) {
                Calendar cld = Calendar.getInstance();
                String now_date = cld.get(Calendar.YEAR) + "年" + (cld.get(Calendar.MONTH) + 1) + "月"
                        + cld.get(Calendar.DAY_OF_MONTH) + "日 " + cld.get(Calendar.HOUR_OF_DAY) + ":"
                        + cld.get(Calendar.MINUTE);
                datime.setText(now_date);
            }
            if (op==1) {
                mID = b.getInt("_ID", -1);
                ToDoList tdl = ThingsUtils.getThing(getApplicationContext(), TB_NAME, mID);

                title.setText(tdl.getTitle());
                remark.setText(tdl.getRemark());
                datime.setText(tdl.getDate());
            }
        }

        btn_dt.setOnClickListener(new MyClickListener());
        OK.setOnClickListener(new MyClickListener());
        RET.setOnClickListener(new MyClickListener());

    }
    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_dateTime:
                    DTUtils.showDialog(moreInfo.this, datime);
                    return ;
                case R.id.OK:
                    String tit = title.getText().toString();
                    String rem = remark.getText().toString();
                    String DateTime = datime.getText().toString();
                    if (mID==-1) {
                        boolean success = ThingsUtils.addThings(getApplicationContext(), TB_NAME, tit, rem, DateTime);

                        if (success) {
                            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        boolean success = ThingsUtils.updThings(getApplicationContext(), TB_NAME, mID, tit, rem, DateTime);

                        if (success) {
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    Intent it = new Intent(moreInfo.this, MainActivity.class);
                    startActivity(it);
                    return ;
                case R.id.RET:
                    Intent itr = new Intent(moreInfo.this, MainActivity.class);
                    startActivity(itr);
            }
        }
    }
}
