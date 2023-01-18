package com.vodka.sotodo;

import android.net.Uri;
import android.provider.CalendarContract;

public class CalenderConstantData {

    // 日历表Uri
    public static final Uri CALENDAR_URI = CalendarContract.Calendars.CONTENT_URI;
    //事件表Uri
    public static final Uri EVENT_URI = CalendarContract.Events.CONTENT_URI;
    //实例表Uri
    public static final Uri INSTANCE_URI = CalendarContract.Instances.CONTENT_URI;
    //提醒表Uri
    public static final Uri REMINDER_URI = CalendarContract.Reminders.CONTENT_URI;

    public static String CALENDARS_NAME = "test";
    public static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
    public static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
    public static String CALENDARS_DISPLAY_NAME = "测试账户";

}
