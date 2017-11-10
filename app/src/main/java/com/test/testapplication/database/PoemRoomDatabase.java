package com.test.testapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by NKommuri on 11/7/2017.
 */

@Database(entities = {Poems.class}, version = 1)
public abstract class PoemRoomDatabase extends RoomDatabase {
    public abstract PoemsDao poemsDao();

}
