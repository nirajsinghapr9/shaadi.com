package com.example.shaadi.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shaadi.BuildConfig;
import com.example.shaadi.model.ResultData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Database(entities = {}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "reputo.db";

    public abstract ContactDao contactDao();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 1;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public void deleteAllData() {
        contactDao().deleteAll();

    }
}

