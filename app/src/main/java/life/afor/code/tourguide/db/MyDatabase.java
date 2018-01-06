package life.afor.code.tourguide.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import life.afor.code.tourguide.db.DAO.UserDao;
import life.afor.code.tourguide.db.model.User;

/**
 * Created by shasha on 6/1/18.
 */
@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase{
    public abstract UserDao userDao();
}
