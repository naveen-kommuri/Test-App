package com.test.testapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by NKommuri on 11/7/2017.
 */

@Dao
public interface PoemsDao {
    @Query("SELECT * FROM poems")
    List<Poems> getAll();

    @Query("SELECT * FROM poems WHERE id IN (:poemIds)")
    List<Poems> loadAllByIds(int[] poemIds);

    @Query("SELECT Poem FROM poems WHERE poem like :meaning")
    List<NameTuple> getPoemBasedMeaning(String meaning);

    @Insert
    void insertAll(Poems... poems);

    @Delete
    void delete(Poems poem);
}
