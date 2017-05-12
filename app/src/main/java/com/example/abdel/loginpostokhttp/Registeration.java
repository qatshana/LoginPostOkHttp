package com.example.abdel.loginpostokhttp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.Request;
import okhttp3.Response;

public class Registeration extends AppCompatActivity implements View.OnClickListener {

    private EditText eTfullName;
    private EditText eTuserName;
    private EditText eTpassword;
    private EditText eTEmail;
    private TextView txtV2;
    private Button btn3;
    private String fullName;
    private String userName;
    private String password;
    private String email;

    private final String url1="http://judeventures.com/api/registerPost.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        eTfullName = (EditText) findViewById(R.id.fullName);
        eTuserName = (EditText) findViewById(R.id.userName);
        eTpassword=(EditText) findViewById(R.id.password);
        eTEmail=(EditText) findViewById(R.id.email);
        btn3=(Button)findViewById(R.id.register);
        txtV2=(TextView)findViewById(R.id.txtV2);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
              fullName=eTfullName.getText().toString();
              userName=eTuserName.getText().toString();
              password=eTpassword.getText().toString();
              email=eTEmail.getText().toString();
              String text;
              RegisterUser ru= new RegisterUser(fullName,userName,password,email);
        try {
            text=ru.execute(url1).get();
            txtV2.setText(text);
            if(text.equals("user or email already exist")) {
                Intent i2 = new Intent(this, MainActivity.class);
                startActivity(i2);
            }else if(text.equals("successfully registered")) {
                Intent i3 = new Intent(this, showProducts.class);
                startActivity(i3);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
      }

/* This class uses the post method to register new user to the database mysql.judeventures.com
* by calling http://judeventures.com/api/registerPost.php
* it takes 4 parameters (name, username, password, and email) and returns "successfully registered" or "user or email already exist"
* */
    private class RegisterUser extends AsyncTask<String,Void,String>{
        String name1;
        String userName1;
        String password1;
        String email1;
        //Class Constructor
        public RegisterUser(String name, String userName, String password, String email){
            this.name1=name;
            this.userName1=userName;
            this.password1=password;
            this.email1=email;
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client1= new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("name", name1)
                    .add("username", userName1)
                    .add("password", password1)
                    .add("email", email1)
                    .build();
            Request request = new Request.Builder()
                    .url(params[0]).post(body).build();
            try {
                Response response = client1.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return "failed to update";
            }
        }
    }
}
