package com.jjf.template.db;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * @author jinjiafeng
 * Time :18-11-10
 * description:
 */
public class TypeConverters {

    @TypeConverter
    public static long dateToLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public static Date longToDate(long time){
        return new Date(time);
    }
}
