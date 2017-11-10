package com.test.testapplication.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.test.testapplication.database.PoemRoomDatabase;
import com.test.testapplication.database.Poems;

import java.util.Random;

/**
 * Created by NKommuri on 11/8/2017.
 */

public class MyApplication extends Application {
    PoemRoomDatabase roomDB;

    @Override
    public void onCreate() {
        super.onCreate();
        roomDB = Room.databaseBuilder(getApplicationContext(), PoemRoomDatabase.class, "sample").build();

    }

    public PoemRoomDatabase getDb() {
        return roomDB;
    }
}
