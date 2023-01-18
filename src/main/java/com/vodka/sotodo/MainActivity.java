package com.vodka.sotodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup m_menu;
    RadioButton m_timer, m_work, m_play, m_life, m_others;
    FrameLayout fl;
    PageHome fg_timer;
    private PagesClass fg_work, fg_play, fg_life, fg_others;
    int table_id;  // 0, 1, 2, 3, 4, 5
    String []cls = {"timer", "tb_work", "tb_play", "tb_life", "tb_others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_menu = findViewById(R.id.m_menu);

        fl = findViewById(R.id.m_container);

        m_work = findViewById(R.id.work);
        m_play = findViewById(R.id.play);
        m_life = findViewById(R.id.life);
        m_others = findViewById(R.id.others);
        m_timer = findViewById(R.id.timer);

        m_timer.setChecked(true);
        FragmentTransaction tsc = getSupportFragmentManager().beginTransaction();
        fg_timer = new PageHome(MainActivity.this);
        tsc.add(R.id.m_container, fg_timer);
        tsc.commit();
        table_id = 0;  // timer

        m_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                hideFrameLayout(transaction);
                switch (checkedId){
                    case R.id.timer:
                        if (fg_timer==null) {
                            fg_timer = new PageHome(MainActivity.this);
                            transaction.add(R.id.m_container, fg_timer);
                        }
                        else { transaction.show(fg_timer); }
                        table_id = 0;
                        break;
                    case R.id.work:
                        if (fg_work==null) {
                            fg_work = new PagesClass(MainActivity.this, "tb_work");
                            transaction.add(R.id.m_container, fg_work);
                        }
                        else { transaction.show(fg_work); }
                        table_id = 1;
                        break;
                    case R.id.play:
                        if (fg_play==null) {
                            fg_play = new PagesClass(MainActivity.this, "tb_play");
                            transaction.add(R.id.m_container, fg_play);
                        }
                        else { transaction.show(fg_play); }
                        table_id = 2;
                        break;
                    case R.id.life:
                        if (fg_life==null) {
                            fg_life = new PagesClass(MainActivity.this, "tb_life");
                            transaction.add(R.id.m_container, fg_life);
                        }
                        else { transaction.show(fg_life); }
                        table_id = 3;
                        break;
                    case R.id.others:
                        if (fg_others==null) {
                            fg_others = new PagesClass(MainActivity.this, "tb_others");
                            transaction.add(R.id.m_container, fg_others);
                        }
                        else { transaction.show(fg_others); }
                        table_id = 4;
                        break;
                }
                transaction.commit();
            }
        });
    }

    public void hideFrameLayout(FragmentTransaction transaction) {
        if (fg_timer!=null) { transaction.hide(fg_timer); }
        if (fg_work!=null) { transaction.hide(fg_work); }
        if (fg_play!=null) { transaction.hide(fg_play); }
        if (fg_life!=null) { transaction.hide(fg_life); }
        if (fg_others!=null) { transaction.hide(fg_others); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.addSubMenu(1, 0, 0, R.string.create);
        menu.addSubMenu(2, 1, 0, R.string.synchronize);
        menu.addSubMenu(3, 2, 0, "关于");
        // menu.addSubMenu(4, 3, 0, "清除");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (table_id==0) {
                    Intent it = new Intent(MainActivity.this, MainAct.class);
                    startActivity(it);
                    Toast.makeText(getApplicationContext(), "Start timer", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent it = new Intent(MainActivity.this, moreInfo.class);
                    it.putExtra("Option", 0);  // 新建
                    it.putExtra("TB_NAME", cls[table_id]);
                    startActivity(it);
                }
                return true;
            case 1:
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                return true;
            case 2:
                if (table_id==0) {
                    AlertDialog.Builder myDlg = new AlertDialog.Builder(MainActivity.this);
                    myDlg.setTitle("关于");
                    myDlg.setMessage("SoToDo version 1.0");
                    myDlg.show();
                }
                else {
                    AlertDialog.Builder myDlg = new AlertDialog.Builder(MainActivity.this);
                    myDlg.setTitle("关于");
                    myDlg.setMessage("1. 点击进入详情页面\n2. 长按删除记录");
                    myDlg.show();
                }
                return true;
            /*
            case 3:      // 清空当前类别的表
                if (table_id==0) {
                    Toast.makeText(getApplicationContext(), "^_^", Toast.LENGTH_LONG).show();
                }
                else {
                    int num = ThingsUtils.clearThings(getApplicationContext(), cls[table_id]);
                    Toast.makeText(getApplicationContext(), "清除成功，共清除" + num + "条记录",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
             */
        }
        return super.onOptionsItemSelected(item);
    }
}
