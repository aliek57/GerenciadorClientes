package com.example.avaliacao3;

import androidx.room.TypeConverter;

import java.util.Date;

public class Conversor {
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromLong(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
