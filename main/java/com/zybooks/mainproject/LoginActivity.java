package com.zybooks.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

// Class that controls the login screen
public class LoginActivity extends AppCompatActivity {

    // Instantiate database
    InventoryDatabase db;

    EditText userInput;
    EditText passInput;

    Button loginButton;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = InventoryDatabase.getInstance(this);

        userInput = findViewById(R.id.usernameText);
        passInput = findViewById(R.id.passwordText);

        loginButton = findViewById(R.id.login_button);
        loginButton.addTextChangedListener(isEmpty);


        register = findViewById(R.id.register_button);
        register.addTextChangedListener(isEmpty);


    }

    private TextWatcher isEmpty = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (userInput.getText().length() > 1 || passInput.getText().length() > 1) {
                loginButton.setEnabled(true);
                register.setEnabled(true);
            }
            else {
                loginButton.setEnabled(false);
                register.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * Log in the user
     * @param view
     */
    public void login(View view) {


        try {
            boolean alreadyLoggedIn = db.login(getUsername(), hash(getPass()));

            if(alreadyLoggedIn) {
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                startActivity(intent);
            } else {
                errorMessage(view.getContext().getResources().getString(R.string.try_again));
            }
        } catch (Exception e) {
            errorMessage(view.getContext().getResources().getString(R.string.try_again));
        }
    }

    /**
     * Basic hash for password security
     * @param pw
     * @return
     * @throws Exception
     */
    private String hash(String pw) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pw.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    /**
     * Retrieves username trimmed and lowercase
     * @return
     */
    private String getUsername() {
        Editable username = userInput.getText();
        return username != null  ? username.toString().trim().toLowerCase() : "";
    }

    /**
     * Retrieves password trimmed
     * @return
     */
    private String getPass() {
        Editable pass = passInput.getText();
        return pass != null ? pass.toString().trim() : "";
    }

    /**
     * Custom error message handler
     * @param message
     */
    private void errorMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Create a new account and add user
     * @param view
     */
    public void createAccount(View view) {


        try {
            boolean userMade = db.addAccount(getUsername(), hash(getPass()));

            if(userMade) {
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                startActivity(intent);
            } else {
                errorMessage(view.getContext().getResources().getString(R.string.try_again));
            }
        } catch (Exception e) {
            errorMessage(view.getContext().getResources().getString(R.string.try_again));
        }
    }
}