package life.afor.code.tourguide.db;

import android.arch.persistence.room.RoomDatabase;

import life.afor.code.tourguide.db.DAO.UserDao;

/**
 * Created by shasha on 6/1/18.
 */

public abstract class MyDatabase extends RoomDatabase{
    public abstract UserDao userDao();
}
