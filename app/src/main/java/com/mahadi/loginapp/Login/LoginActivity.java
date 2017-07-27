package com.mahadi.loginapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mahadi.loginapp.Dashboard.DashboardActivity;
import com.mahadi.loginapp.R;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText_email, editText_password;
    public final String serverUrl = "http://54.218.102.254/scarecrow/json.php";

    Button btn_login;
    public String entered_email;
    Resources resources;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String entered_password;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String KEY_EMAIL="email";
    public static  final String KEY_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_email = (EditText) findViewById(R.id.input_email);
        editText_password = (EditText) findViewById(R.id.input_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        btn_login.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {

        if(v == btn_login){
//           editText_email.setText("");
//           editText_password.setText("");
            login();
        }
    }
    private void login(){
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();
        InputMethodManager im = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        userLogin(email, password);
    }

    private void userLogin(final String email, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_EMAIL, email);
                    editor.putString(KEY_PASSWORD, password);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("email",params[0]);
                data.put("password",params[1]);

                RegisterHandler rhc = new RegisterHandler();

                String result = rhc.sendPostRequest(serverUrl,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(email,password);
    }
}
