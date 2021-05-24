package com.example.shaadi.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shaadi.model.ResultData;

import java.util.List;


@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResultData contact);

    @Update
    void update(ResultData... contacts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ResultData> contactList);

    @Query("Select * from result")
    List<ResultData> getAllContacts();

    @Query("Delete from result")
    void deleteAll();
}
