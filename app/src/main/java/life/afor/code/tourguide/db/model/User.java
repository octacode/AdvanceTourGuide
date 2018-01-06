package life.afor.code.tourguide.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by shasha on 6/1/18.
 */

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    int id;

    @ColumnInfo
    String firstName;

    @ColumnInfo
    String lastName;

    @ColumnInfo
    String contactNo;

    @ColumnInfo
    String password;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getPassword() {
        return password;
    }
}
