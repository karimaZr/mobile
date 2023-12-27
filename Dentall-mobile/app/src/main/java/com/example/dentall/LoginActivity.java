package com.example.dentall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dentall.beans.Student;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class LoginActivity extends AppCompatActivity {

    private List<Student> allStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        MaterialButton loginButton = findViewById(R.id.loginbtn);

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.198.139:8083/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the API service
        ApiService apiService = retrofit.create(ApiService.class);

        // Fetch students from the server
        Call<List<Student>> call = apiService.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    allStudents = response.body();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to fetch students", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // Move the checkCredentialsAndNavigate call inside onResponse
                checkCredentialsAndNavigate(enteredUsername, enteredPassword);
            }
        });
    }

    private void checkCredentialsAndNavigate(String enteredUsername, String enteredPassword) {
        Student loggedInStudent = getLoggedInStudent(enteredUsername, enteredPassword);

        if (loggedInStudent != null) {
            // Ici, vous pouvez extraire l'id de l'utilisateur connecté
            int userId = loggedInStudent.getId();
            Log.d("LoginActivity", "User ID: " + userId);

            // Poursuivez avec la navigation vers MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userId", userId); // Transférer l'ID à MainActivity si nécessaire
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private Student getLoggedInStudent(String username, String password) {
        if (allStudents != null) {
            for (Student student : allStudents) {
                if (student.getUserName().equals(username) && student.getPassword().equals(password)) {
                    return student;
                }
            }
        }
        return null;
    }
}


