package com.vodka.sotodo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// 自动绘制view并且填充数据
public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<ToDoList> iToDoList;

    // context和数据源
    ListAdapter(Context context, List<ToDoList> listInfo) {
        this.context = context;
        this.iToDoList = listInfo;
    }

    // 列表项数目，也是getView()需要执行的次数
    public int getCount() { return iToDoList.size(); }
    public Object getItem(int position) { return null; }
    public long getItemId(int position) { return position; }

    // 循环执行getView绘制列表项
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        InfoView infoV;
        // 判断是否创建过view，若创建过就不再创建，优化内存占用
        if (convertView!=null) { infoV = (InfoView) convertView.getTag(); }
        else {
            // 未创建过则创建新的
            infoV = new InfoView();
            // 从XML文件info_view中实例化View对象
            convertView = LayoutInflater.from(context).inflate(R.layout.info_view, null);
            infoV.title = convertView.findViewById(R.id.mTitle);
            infoV.time = convertView.findViewById(R.id.mTime);
            convertView.setTag(infoV);
        }

        ToDoList info = iToDoList.get(position);
        infoV.title.setText(info.getTitle());

        infoV.time.setText(info.getDate());

        return convertView;
    }

    public class InfoView {
        TextView title;  // 标题
        TextView time;	// 时间
    }
}
