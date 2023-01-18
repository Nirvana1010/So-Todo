package com.vodka.sotodo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<CalenderBean> eventInfoList;

    public MyAdapter(FirstActivity mainActivity, List<CalenderBean> list){
        this.context = mainActivity;
        this.eventInfoList = list;
    }
    @Override
    public int getCount() throws NullPointerException{
        int count = eventInfoList.size();
        if(count==0){
            return 0;
        }
        return count;
    }
    @Override
    public Object getItem(int position) {
        return eventInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_view,null);

            holder.title = (TextView) convertView.findViewById(R.id.item_title);
            holder.start = (TextView)convertView.findViewById(R.id.item_start_time);
            holder.end = (TextView)convertView.findViewById(R.id.item_end_time);
            holder.position = (TextView)convertView.findViewById(R.id.item_todo_postion);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(eventInfoList.get(position).eventTitle);
        String start_tim = String.valueOf(eventInfoList.get(position).startTime);
        holder.start.setText(start_tim);
        String end_tim = String.valueOf(eventInfoList.get(position).endTime);
        holder.end.setText(end_tim);
        holder.position.setText(position+1+"");
        return convertView;
    }

    class ViewHolder{
        TextView title;
        TextView start;
        TextView end;
        TextView position;
    }
}
