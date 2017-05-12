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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static android.R.attr.name;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText eTuserName;
    private EditText eTpassword;
    private TextView txtV1;

    private Button btn1;
    private Button btn2;

    private String userName;
    private String password;

    private final String url1="http://www.judeventures.com/api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eTuserName = (EditText) findViewById(R.id.userName);
        eTpassword=(EditText) findViewById(R.id.password);
        btn1=(Button)findViewById(R.id.userLogin);
        btn2=(Button)findViewById(R.id.clearText);
        txtV1=(TextView)findViewById(R.id.txtV1);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    /* Code to check which button i spress
    *  We are using two buttons: one for login and one for new user registeration
    *  userLogin2() is called when Login button is pressed
    *  New Activity (Registreration Activity) is called if Registeration Button is pressed
    * */
    @Override
    public void onClick(View v) {
    // Login Button
    if(v==btn1){
        userLogin2();
    }
     // New User Registeration Button
        if(v==btn2){
            Intent i= new Intent(this, Registeration.class);
            startActivity(i);
        }

    }

/* This function reads two variable: username and passwords
* it then checkes the database: mysql.judeventures.com by calling
* www.judeventures.com/api/login.php using Post Method to check if user is registerd
*  it then will return success or invalid username or password
*  if response is success, it will start new activity (show products)
* */
    public void userLogin2(){
        userName=eTuserName.getText().toString();
        password=eTpassword.getText().toString();
        UserLogin ulc2=new UserLogin(userName,password);
        try {
            String txt3=ulc2.execute(url1).get();
            txtV1.setText(txt3);
            if(txt3.equals("success")) {
                Intent i4 = new Intent(this, showProducts.class);
                startActivity(i4);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // This OkHttp class uses the post method and returns String
    // Object is istantiated by using: UserLogin ulc=new UserLogin(userName,password);

    private class UserLogin extends AsyncTask<String,Void,String>{
        OkHttpClient client = new OkHttpClient();
        private String userName1, pass1;
        public UserLogin(String userName, String pass){
            this.userName1 = userName;
            this.pass1 = pass;
        }
        @Override
        protected String doInBackground(String... params) {
            RequestBody body = new FormBody.Builder()
                    .add("username", userName1)
                    .add("password", pass1)
                    .build();
            Request request = new Request.Builder()
                    .url(params[0]).post(body).build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userName1+ "  "+pass1;
        }
    }
}
