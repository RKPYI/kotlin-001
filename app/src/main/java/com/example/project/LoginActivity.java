package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Static credentials (real + demo)
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    private static final String DEMO_USERNAME = "demo";
    private static final String DEMO_PASSWORD = "demo123";

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(this);

        // If already logged in, skip to main
        if (session.isLoggedIn()) {
            goToMainAndClearBackstack();
            return;
        }

        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(u)) { etUsername.setError("Required"); return; }
            if (TextUtils.isEmpty(p)) { etPassword.setError("Required"); return; }

            // ✅ Accept admin or demo login
            if ((u.equals(USERNAME) && p.equals(PASSWORD)) ||
                    (u.equals(DEMO_USERNAME) && p.equals(DEMO_PASSWORD))) {

                session.setLoggedIn(true);
                goToMainAndClearBackstack();

            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMainAndClearBackstack() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
