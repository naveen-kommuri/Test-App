package com.test.testapplication.database;

import android.arch.persistence.room.ColumnInfo;

public class NameTuple {
    @ColumnInfo(name = "Poem")
    public String poem;
}