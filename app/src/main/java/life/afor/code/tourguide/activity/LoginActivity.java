package life.afor.code.tourguide.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.db.MyDatabase;
import life.afor.code.tourguide.db.model.User;
import life.afor.code.tourguide.utils.Preferences;


public class LoginActivity extends AppCompatActivity {

    private TextInputEditText nameET, passwordET;
    private Button login;
    private TextView register;

    private MyDatabase myDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDatabase = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.class, "my-db").build();

        nameET = (TextInputEditText) findViewById(R.id.username_tv);
        passwordET = (TextInputEditText) findViewById(R.id.password_tv);
        register = (TextView) findViewById(R.id.register_button);
        login = (Button) findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameET.getText().toString().trim().length() == 0)
                    nameET.setError("Can't be left empty");
                else if (passwordET.getText().toString().trim().length() == 0)
                    passwordET.setError("Can't be left empty");
                else {
                    try {
                        List<User> allUsers = new FetchUsers().execute().get();
                        int index = getIndexOf(allUsers, nameET.getText().toString(), passwordET.getText().toString());
                        if (index < 0)
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        else {
                            Preferences.setLoggedIn(LoginActivity.this);
                            Preferences.setUserID(LoginActivity.this, index);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private int getIndexOf(List<User> allEmps, String username, String password) {
        for (int i = 0; i < allEmps.size(); i++) {
            if (username.matches(allEmps.get(i).getFirstName()) && password.matches(allEmps.get(i).getPassword())) {
                return allEmps.get(i).getId();
            }
        }
        return -1;
    }

    private class FetchUsers extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            return myDatabase.userDao().fetchAllUsers();
        }
    }
}
