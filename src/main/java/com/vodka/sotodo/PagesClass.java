package com.vodka.sotodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

public class PagesClass extends Fragment {
    private Context context;
    private String TB_NAME;

    public PagesClass() {}

    PagesClass(Context context, String TB_NAME) {
        this.context = context;
        this.TB_NAME = TB_NAME;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail, container, false);

        final ListView todoList = view.findViewById(R.id.things);
        final List<ToDoList> iTodoList = ThingsUtils.getThings(context, TB_NAME);

        final ListAdapter mAp = new ListAdapter(context, iTodoList);

        todoList.setAdapter(mAp);

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 显示点击项
                String title = iTodoList.get(position).getTitle();
                Toast.makeText(context, title, Toast.LENGTH_SHORT).show();

                Intent it = new Intent(context, moreInfo.class);
                it.putExtra("Option", 1);
                it.putExtra("TB_NAME", TB_NAME);
                int cid = iTodoList.get(position).getId();
                it.putExtra("_ID", cid);
                startActivity(it);
            }
        });

        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder myDlg = new AlertDialog.Builder(context);
                myDlg.setTitle("确认");
                myDlg.setMessage("确定删除所选内容？");
                myDlg.setNegativeButton("取消", null);
                myDlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int cid = iTodoList.get(position).getId();
                        boolean success = ThingsUtils.delThings(context, TB_NAME, cid);
                        if (success) {
                            if (iTodoList.remove(position)!=null) {
                            Toast.makeText(getContext(), "删除成功",
                                    Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getContext(), "删除失败",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
                        }

                        mAp.notifyDataSetChanged();
                    }
                });
                myDlg.show();

                return true;
            }
        });
        return view;
    }
}
