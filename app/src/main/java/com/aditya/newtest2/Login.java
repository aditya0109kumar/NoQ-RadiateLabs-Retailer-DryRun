package com.aditya.newtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    private static final String FILE_NAME = "myFile";
    private long backPressedTime;
    private  Toast backToast;
    //    String Email = getIntent().getStringExtra("Email");
//    String Password = getIntent().getStringExtra("Password");
//    int Store_ID=0;
    Animation topAnim, bottomAnim;
    ProgressDialog progressDialog;
    String emailloginstring, passwordloginstring, StoreEmail, StorePassword;
    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView newUserLink, forgotPasswordLink;



    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";
    public String error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.loginEditTextEmail);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        loginPassword = (EditText) findViewById(R.id.loginEditTextPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        newUserLink = (TextView) findViewById(R.id.new_user_link);
        forgotPasswordLink = (TextView) findViewById(R.id.forgot_password_link);


        loginEmail.setAnimation(bottomAnim);
        loginPassword.setAnimation(bottomAnim);


//        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        String mail = sharedPreferences.getString("email", "No Email Stored");
//        String password = sharedPreferences.getString("password", "Password");


        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (loginEmail.getText().toString().trim().length() > 0 || loginPassword.getText().toString().trim().length() > 0) {
//                    Toast.makeText(Login.this, "Fill the all details", Toast.LENGTH_SHORT).show();
//                } else {
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.show();

                ConnectMySqlLogin connectMySql = new ConnectMySqlLogin();
                connectMySql.execute("");
//                }
            }
        });

        newUserLink.setOnClickListener(view -> {
            Intent o = new Intent(Login.this, MainActivity2.class);
            startActivity(o);
        });

    }

    private class ConnectMySqlLogin extends AsyncTask<String, Void, String> {
        String res = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Login.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");
                StoreEmail = loginEmail.getText().toString();
                StorePassword = loginPassword.getText().toString();

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
//                ResultSet rs =
                String sql = "SELECT Store_ID, Email FROM New_Store_Table WHERE Email = '" + StoreEmail + "' AND Password = '" + StorePassword + "'";
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    // for the shared preferences used in the login
//                    editor.putString("email", loginEmail.getText().toString());
//                    editor.commit();

                    String spemail, sppassword;
                    spemail = loginEmail.getText().toString();
                    sppassword = loginPassword.getText().toString();
                    StoredDataUsingSharedPref(spemail, sppassword);

                    Intent i = new Intent(Login.this, HomeActivity.class);
//                    i.putExtra("STORE_EMAIL", StoreEmail);
//                    i.putExtra("STORE_PASSWORD", StorePassword);
                    startActivity(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }


            return res;
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();


//            if (rememberMeChkBox.isChecked()) {
//
//            }


        }
    }

    private void StoredDataUsingSharedPref(String spemail, String sppassword) {

        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("email", spemail);
        editor.putString("password", sppassword);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime+2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}