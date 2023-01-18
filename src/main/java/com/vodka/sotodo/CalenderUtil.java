package com.vodka.sotodo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;
import static com.vodka.sotodo.CalenderConstantData.CALENDARS_ACCOUNT_NAME;
import static com.vodka.sotodo.CalenderConstantData.CALENDARS_ACCOUNT_TYPE;
import static com.vodka.sotodo.CalenderConstantData.CALENDARS_DISPLAY_NAME;
import static com.vodka.sotodo.CalenderConstantData.CALENDARS_NAME;

public class CalenderUtil {

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    private static int checkCalendarAccount(Context context) throws SecurityException{
        Cursor userCursor = context.getContentResolver().query(CalenderConstantData.CALENDAR_URI, null, null, null, null);
        try {
            if (userCursor == null) { //查询返回空值
                return -1;
            }
            int count = userCursor.getCount();
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     */
    private static int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if( oldId >= 0 ){
            return oldId;
        }else{
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回账户id，否则返回-1
     */
    private static long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = CalenderConstantData.CALENDAR_URI;
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    /**
     * 查询日历日程，将所有日程显示在ListView中
     *
     * @param context
     * @return
     */
    public static List<CalenderBean> queryCalendarEvent(Context context)throws SecurityException{

        Cursor cursor = context.getContentResolver().query(CalenderConstantData.EVENT_URI, null, null, null, null);
        List<CalenderBean> calendarEventList = null;
        try{
            if(cursor.moveToFirst()){
                calendarEventList = new ArrayList<>();
                do{
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String location = cursor.getString(cursor.getColumnIndex("eventLocation"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    //日程开始时间
                    long startTimeMillis = cursor.getLong(cursor.getColumnIndex("dtstart"));
                    //日程结束时间
                    long endTimeMillis = cursor.getLong(cursor.getColumnIndex("dtend"));
                    Log.i(TAG,"long_time_is"+"  "+startTimeMillis+"\n"+"long_end_is"+"  "+endTimeMillis+"\n");
                    long curTimeMillis = Calendar.getInstance().getTimeInMillis();  //获取当前时间
                    long event_ID = cursor.getLong(cursor.getColumnIndex("_id"));
                    Log.i(TAG,"event_id_is"+event_ID+"\n");

                    String startTime = timeStamp2Date(startTimeMillis);
                    String endTime = timeStamp2Date(endTimeMillis);

                    CalenderBean calendarEvent = new CalenderBean(startTime,endTime,title,description,location,startTimeMillis,endTimeMillis,curTimeMillis,event_ID);
                    calendarEventList.add(calendarEvent);
                    Log.i(TAG,"eventTitle: " + title + "\n" +
                            "description: " + description + "\n" +
                            "location: " + location + "\n" +
                            "startTime: " + startTime + "\n" +
                            "endTime: " + endTime + "\n"
                    );
                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return  calendarEventList;
    }

    public static String timeStamp2Date(long time){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static long getString2timeStamp(String time){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sf.parse(time);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static int deleteCalendarEvent(Context context, long sys_id)throws SecurityException{

        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,sys_id);
        int rows = context.getContentResolver().delete(deleteUri,null,null);
        return rows;
    }

    public static int updateCalendar(Context context,String title, long sys_id, String start_time, String end_time)throws SecurityException{

        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,sys_id);
        int row = context.getContentResolver().delete(deleteUri,null,null);

        //Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,sys_id);
        int cal_id = checkAndAddCalendarAccount(context);
        if(cal_id<0){return -1;}

        ContentValues values = new ContentValues();
        long start2 = getString2timeStamp(start_time);
        long end2 = getString2timeStamp(end_time);
        values.put("calendar_id",cal_id);
        values.put("title",title);
        values.put(CalendarContract.Events.DTSTART, start2);
        values.put(CalendarContract.Events.DTEND, end2);
        values.put(CalendarContract.Events.EVENT_TIMEZONE,"Asia/Shanghai");
        Uri newEventUri = context.getContentResolver().insert(CalenderConstantData.EVENT_URI,values);
        //int rows = context.getContentResolver().update(updateUri,values,null,null);
        long eventID = newEventUri==null?-1:ContentUris.parseId(newEventUri);
        return row;
    }

}
