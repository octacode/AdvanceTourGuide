package life.afor.code.tourguide.db.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import life.afor.code.tourguide.db.model.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(User user);

    @Query("Select * from user")
    List<User> fetchAllUsers();

    @Query("Select * from user where contactNo =:contactNo")
    User getUser(String contactNo);

    @Update
    public void updateUser(User user);
}
