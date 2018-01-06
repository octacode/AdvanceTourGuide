package life.afor.code.tourguide.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.db.MyDatabase;
import life.afor.code.tourguide.db.model.User;
import life.afor.code.tourguide.utils.Preferences;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText firstName, lastName, contactNo, password, confirmPassword;
    private Button register;
    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setValues();

        myDatabase = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.class, "my-db").build();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstName.getText().toString().trim().length() == 0)
                    firstName.setError("Can't be left empty");
                else if(lastName.getText().toString().length() == 0)
                    lastName.setError("Can't be left empty");
                else if(contactNo.getText().toString().length() == 0)
                    contactNo.setError("Can't be left empty");
                else if(password.getText().toString().length() == 0)
                    password.setError("Can't be left empty");
                else if(confirmPassword.getText().toString().trim().length() == 0)
                    confirmPassword.setError("Can't be left empty");
                else if(!password.getText().toString().matches(confirmPassword.getText().toString()))
                    confirmPassword.setError("Password doesn't match");
                else {
                    User user = new User();
                    user.setFirstName(firstName.getText().toString().trim());
                    user.setLastName(lastName.getText().toString().trim());
                    user.setContactNo(contactNo.getText().toString().trim());
                    user.setPassword(password.getText().toString().trim());
                    new InsertDb().execute(user);
                    Preferences.setUserID(RegisterActivity.this, user.getContactNo());
                    Preferences.setLoggedIn(RegisterActivity.this);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        });

    }

    private void setValues() {
        firstName = (TextInputEditText)findViewById(R.id.first_name);
        lastName = (TextInputEditText)findViewById(R.id.last_name);
        contactNo = (TextInputEditText)findViewById(R.id.contact_no);
        password = (TextInputEditText)findViewById(R.id.password);
        confirmPassword = (TextInputEditText)findViewById(R.id.confirm_password);
        register = (Button)findViewById(R.id.register);
    }

    private class InsertDb extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            myDatabase.userDao().insertUser(users[0]);
            return null;
        }
    }
}
