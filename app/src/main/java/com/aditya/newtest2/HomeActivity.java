package com.aditya.newtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.mysql.jdbc.PreparedStatement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";
    private static final String FILE_NAME ="myFile";
    public String error = "", StoreEmail, StorePassword, emailHomeSP, passHomeSP;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private long backPressedTime;
    private  Toast backToast;


    public String Store_Name = "@", Store_Location = "@", Store_Address = "@", Store_City = "@", Pincode = "@", Store_State = "@", Store_Country = "@", Phone_No = "@", in_store = "@", takeaway = "@", home_delivery = "@";
    Integer Delivery_Charge = 0, Min_Charge = 0, Max_Charge = 0;

    TextView sName, sLocation, sAddress, sCity, sPincode, sState, sCountry, sPhoneNo, sInStore, sTakeAway, sHomeDelivery, sDeliveryCharge, sMinCharge, sMaxCharge;
    Button EditButton, LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_acitvity);

        StoreEmail = getIntent().getStringExtra("STORE_EMAIL");
        StorePassword = getIntent().getStringExtra("STORE_PASSWORD");

//        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
//        emailHomeSP = sharedPreferences.getString("spemail", "Data not Found");
//        passHomeSP = sharedPreferences.getString("sppassword", "Data not Found");

        sName = (TextView) findViewById(R.id.Store_Name);
        sLocation = (TextView) findViewById(R.id.Store_Location);
        sAddress = (TextView) findViewById(R.id.Store_Address);
        sCity = (TextView) findViewById(R.id.Store_City);
        sPincode = (TextView) findViewById(R.id.Pincode);
        sState = (TextView) findViewById(R.id.Store_State);
        sCountry = (TextView) findViewById(R.id.Store_Country);
        sPhoneNo = (TextView) findViewById(R.id.Phone_No);
        sInStore = (TextView) findViewById(R.id.in_store);
        sTakeAway = (TextView) findViewById(R.id.takeaway);
        sHomeDelivery = (TextView) findViewById(R.id.home_delivery);
        sDeliveryCharge = (TextView) findViewById(R.id.Delivery_Charge);
        sMinCharge = (TextView) findViewById(R.id.Min_Charge);
        sMaxCharge = (TextView) findViewById(R.id.Max_Charge);
        EditButton = (Button) findViewById(R.id.editbtn);
        LogoutBtn = (Button) findViewById(R.id.logoutBtn);

        ConnectMySqlHome connectMySql = new ConnectMySqlHome();
        connectMySql.execute("");

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditPage();
            }
        });

        LogoutBtn.setOnClickListener(view -> {

//            SharedPreferences preferences =getSharedPreferences(FILE_NAME,MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
//            editor.apply();
//            finish();
            Intent o = new Intent(HomeActivity.this,MainActivity2.class);
            startActivity(o);
        });
    }


    private class ConnectMySqlHome extends AsyncTask<String, Void, String> {
        String res = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(HomeActivity.this, "Connecting", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");
//                emailloginstring = loginEmail.getText().toString();
//                passwordloginstring = loginPassword.getText().toString();

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
//                ResultSet rs =
                String sql = "SELECT `Store_Name`, `Store_Location`, `Store_Address`, `Store_City`, `Pincode`, " +
                        "`Store_State`, `Store_Country`, `Phone_No`, `in_store`, `takeaway`, `home_delivery`, `Delivery_Charge`, " +
                        "`Min_Charge`, `Max_Charge` FROM `New_Store_Table` WHERE Email = '" + StoreEmail + "' AND Password = '" + StorePassword + "'";

                ps = (PreparedStatement) con.prepareStatement(sql);

//                rs = st.executeQuery(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Store_Name = rs.getString("Store_Name");
                    Store_Location = rs.getString("Store_Location");
                    Store_Address = rs.getString("Store_Address");
                    Store_City = rs.getString("Store_City");
                    Pincode = rs.getString("Pincode");
                    Store_State = rs.getString("Store_State");
                    Store_Country = rs.getString("Store_Country");
                    Phone_No = rs.getString("Phone_No");
                    in_store = rs.getString("in_store");
                    takeaway = rs.getString("takeaway");
                    home_delivery = rs.getString("home_delivery");
                    Delivery_Charge = rs.getInt("Delivery_Charge");
                    Min_Charge = rs.getInt("Min_Charge");
                    Max_Charge = rs.getInt("Max_Charge");
                }


//
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(HomeActivity.this, result, Toast.LENGTH_SHORT).show();
            sName.setText(Store_Name);
            sLocation.setText(Store_Location);
            sAddress.setText(Store_Address);
            sCity.setText(Store_City);
            sPincode.setText(Pincode);
            sState.setText(Store_State);
            sCountry.setText(Store_Country);
            sPhoneNo.setText(Phone_No);
            sInStore.setText(in_store);
            sTakeAway.setText(takeaway);
            sHomeDelivery.setText(home_delivery);
            String DC = Integer.toString(Delivery_Charge);
            sDeliveryCharge.setText(DC);
            String MnC = Integer.toString(Min_Charge);
            sMinCharge.setText(MnC);
            String MxC = Integer.toString(Max_Charge);
            sMaxCharge.setText(MxC);
        }
    }

    private void goToEditPage() {
        Intent intent = new Intent(HomeActivity.this, DataEditForm.class);
        intent.putExtra("Email", StoreEmail);
        intent.putExtra("Password", StorePassword);
        startActivity(intent);
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