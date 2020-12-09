package com.aditya.newtest2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainActivity2 extends AppCompatActivity {

    EditText email, password, cPassword;
    ProgressDialog progressDialog;
    Animation topAnim, bottomAnim;
    ImageView userLogo;
    String emailQ, passQ, cPassQ;
    TextView alreadyRegistered;
    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";
    public String error="";
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        userLogo = (ImageView) findViewById(R.id.register_user_logo);
        email = (EditText) findViewById(R.id.editTextEmailRegister);
        password = (EditText) findViewById(R.id.editTextPassword);
        cPassword = (EditText) findViewById(R.id.editTextConfPassword);
        register = (Button) findViewById(R.id.registerButton);
        alreadyRegistered = (TextView) findViewById(R.id.already_registered_link);


        userLogo.setAnimation(topAnim);
        email.setAnimation(bottomAnim);
        password.setAnimation(bottomAnim);
        cPassword.setAnimation(bottomAnim);


        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                progressDialog = new ProgressDialog(MainActivity2.this);
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.show();

                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
            }
        });

        alreadyRegistered.setOnClickListener(view -> {
            Intent o = new Intent(MainActivity2.this,Login.class);
            startActivity(o);
        });
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtData.setText("");
//            }
//        });

    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity2.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");
                emailQ = email.getText().toString();
                passQ = password.getText().toString();
                cPassQ = cPassword.getText().toString();

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
//                ResultSet rs =
                st.executeUpdate("INSERT INTO `New_Store_Table`(`Email`, `Password`) VALUES ('"+emailQ+"','"+passQ+"')");
//                ResultSetMetaData rsmd = rs.getMetaData();

//                while (rs.next()) {
//                    result += rs.getString(1).toString() + "\n";
//                }
//                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(MainActivity2.this, result, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), Login.class));

            progressDialog.dismiss();

            
        }
    }

}