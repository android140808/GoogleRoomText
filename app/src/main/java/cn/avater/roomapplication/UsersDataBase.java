package cn.avater.roomapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class UsersDataBase extends RoomDatabase {
    private static final String DB_NAME = "My.db";
    private static volatile UsersDataBase instance;

    public static UsersDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (UsersDataBase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }
        }
        return instance;
    }

    private static UsersDataBase create(final Context context) {
        return Room.databaseBuilder(context,
                UsersDataBase.class,
                DB_NAME)
//                .allowMainThreadQueries()//允许在主线程操作数据库
                .build();
    }

    public abstract UsersDao getUsersDao();
}
