package com.aditya.newtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.os.StrictMode;

public class DataEditForm extends AppCompatActivity {


    private static final String url = "jdbc:mysql://ec2-13-234-120-100.ap-south-1.compute.amazonaws.com:3306/myDB";
    private static final String user = "admin";
    private static final String pass = "Radiate@123";
    boolean doubleBackToExitPressedOnce = false;
    public String STORE_EMAIL , STORE_PASSWORD , error = "";
    ImageView backArrow;
    Button save, cancel;
    PreparedStatement ps=null, psUpdate=null;
    ResultSet rs=null, rsUpdate=null;
    EditText editsName, editsLocation, editsAddress, editsCity, editsPincode, editsState, editsCountry, editsPhone_No, editsin_store, editstakeaway, editshome_delivery, editsDelivery_Charge, editsMin_Charge, editsMax_Charge;

    public String Store_Name = "@", Store_Location = "@", Store_Address = "@", Store_City = "@", Pincode = "@", Store_State = "@", Store_Country = "@", Phone_No = "@", in_store = "@", takeaway = "@", home_delivery = "@";
    Integer Delivery_Charge = 0, Min_Charge = 0, Max_Charge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data_edit_form);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        STORE_EMAIL = getIntent().getStringExtra("Email");
        STORE_PASSWORD = getIntent().getStringExtra("Password");
        editsName = (EditText) findViewById(R.id.edt1);
        editsLocation = (EditText) findViewById(R.id.edt2);
        editsAddress = (EditText) findViewById(R.id.edt3);
        editsCity = (EditText) findViewById(R.id.edt4);
        editsPincode = (EditText) findViewById(R.id.edt5);
        editsState = (EditText) findViewById(R.id.edt6);
        editsCountry = (EditText) findViewById(R.id.edt7);
        editsPhone_No = (EditText) findViewById(R.id.edt8);
        editsin_store = (EditText) findViewById(R.id.edt9);
        editstakeaway = (EditText) findViewById(R.id.edt10);
        editshome_delivery = (EditText) findViewById(R.id.edt11);
        editsDelivery_Charge = (EditText) findViewById(R.id.edt12);
        editsMin_Charge = (EditText) findViewById(R.id.edt13);
        editsMax_Charge = (EditText) findViewById(R.id.edt14);
        save = (Button) findViewById(R.id.editDetailsSaveBtn);
        cancel = (Button) findViewById(R.id.editCancelBtn);

        ConnectMySqlHome connectMySql = new ConnectMySqlHome();
        connectMySql.execute("");

        save.setOnClickListener(view -> saveAllTheDetails());

        cancel.setOnClickListener(view -> {
            Intent intent = new Intent(DataEditForm.this, HomeActivity.class);
            intent.putExtra("STORE_EMAIL", STORE_EMAIL);
            intent.putExtra("STORE_PASSWORD", STORE_PASSWORD);
            startActivity(intent);
        });

//        backArrow.setOnClickListener(view -> {
//            Intent intent = new Intent(DataEditForm.this, HomeActivity.class);
//            intent.putExtra("STORE_EMAIL", STORE_EMAIL);
//            intent.putExtra("STORE_PASSWORD", STORE_PASSWORD);
//            startActivity(intent);
//        });

    }

    private void saveAllTheDetails() {

        String[] updates = {editsName.getText().toString(), editsLocation.getText().toString(), editsAddress.getText().toString(), editsCity.getText().toString(), editsPincode.getText().toString(), editsState.getText().toString(), editsCountry.getText().toString(), editsPhone_No.getText().toString(), editsin_store.getText().toString(), editstakeaway.getText().toString(), editshome_delivery.getText().toString()};
        Integer[] intUpdates = {Integer.parseInt(editsDelivery_Charge.getText().toString()), Integer.parseInt(editsMin_Charge.getText().toString()), Integer.parseInt(editsMax_Charge.getText().toString())};
        String updateQuery = "UPDATE `New_Store_Table` SET `Store_Name`= '"+updates[0]+"',`Store_Location`='"+updates[1]+"',`Store_Address`='"+updates[2]+"',`Store_City`='"+updates[3]+"',`Pincode`='"+updates[4]+"',`Store_State`='"+updates[5]+"',`Store_Country`='"+updates[6]+"',`Phone_No`='"+updates[7]+"',`in_store`='"+updates[8]+"',`takeaway`='"+updates[9]+"',`home_delivery`='"+updates[10]+"',`Delivery_Charge`='"+intUpdates[0]+"', `Min_Charge`="+intUpdates[0]+",`Max_Charge`="+intUpdates[0]+" WHERE Email = '"+STORE_EMAIL+"' AND Password = '"+STORE_PASSWORD+"'";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conU = DriverManager.getConnection(url, user, pass);
            psUpdate = (PreparedStatement) conU.prepareStatement(updateQuery);
            psUpdate.executeUpdate();
            Toast.makeText(this, "Details Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DataEditForm.this, HomeActivity.class);
            intent.putExtra("STORE_EMAIL", STORE_EMAIL);
            intent.putExtra("STORE_PASSWORD", STORE_PASSWORD);
            startActivity(intent);

        }

        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    private class ConnectMySqlHome extends AsyncTask<String, Void, String> {
        String res = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DataEditForm.this, "Connecting", Toast.LENGTH_SHORT)
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
                        "`Min_Charge`, `Max_Charge` FROM `New_Store_Table` WHERE Email = '" + STORE_EMAIL + "' AND Password = '" + STORE_PASSWORD + "'";

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

                con.close();
//
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(DataEditForm.this, result, Toast.LENGTH_SHORT).show();
            editsName.setText(Store_Name);
            editsLocation.setText(Store_Location);
            editsAddress.setText(Store_Address);
            editsCity.setText(Store_City);
            editsPincode.setText(Pincode);
            editsState.setText(Store_State);
            editsCountry.setText(Store_Country);
            editsPhone_No.setText(Phone_No);
            editsin_store.setText(in_store);
            editstakeaway.setText(takeaway);
            editshome_delivery.setText(home_delivery);
            String DC = Integer.toString(Delivery_Charge);
            editsDelivery_Charge.setText(DC);
            String MnC = Integer.toString(Min_Charge);
            editsMin_Charge.setText(MnC);
            String MxC = Integer.toString(Max_Charge);
            editsMax_Charge.setText(MxC);
        }
    }

    @Override
    public void onBackPressed() {
//
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
    }
}