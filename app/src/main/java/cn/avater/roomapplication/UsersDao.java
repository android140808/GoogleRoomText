package cn.avater.roomapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;


@Dao
public interface UsersDao {
    @Query("select * from Users")
    Flowable<List<Users>> getUsers();

    @Query("select * from Users where id = :id")
    Flowable<Users> getUsersById(int id);

    @Query("select * from Users where id = :id")
    Flowable<List<Users>> getUserById(int id);

    @Insert
    void insert(Users users);

    @Delete
    void delete(Users users);

    @Update
    void update(Users users);
}
