package com.popo.leancloudtest.moudles.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.popo.leancloudtest.R;
import com.popo.leancloudtest.moudles.Chat.ChatScreen;
import com.popo.leancloudtest.moudles.signin.SigninActivity;
import com.popo.leancloudtest.utils.SharedPreferenceManeger;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences=null;
    private EditText username=null;
    private EditText password=null;
    private Button signin=null;
    private Button login=null;
    private ProgressBar progressBar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signin);
        login=(Button)findViewById(R.id.login);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, SigninActivity.class);
                startActivityForResult(intent,0);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn(username.getText().toString(),password.getText().toString());
            }
        });

        sharedPreferences= SharedPreferenceManeger.getInstance(getApplicationContext());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                String un=data.getStringExtra("username");
                String psw=data.getStringExtra("password");
                username.setText(un);
                password.setText(psw);
                break;
            default:
                break;
        }
    }

    private void LogIn(final String un,final String psw){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        AVUser.logInInBackground(un, psw, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e==null){
                    Intent intent=new Intent(LoginActivity.this, ChatScreen.class);
                    startActivity(intent);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    sharedPreferences.edit()
                            .putBoolean("isLogin",true)
                            .putString("username",un)
                            .putString("password",psw)
                            .commit();
                    finish();
                }else {
                    Log.d("error",e.getMessage());
                }
            }
        });
    }
}
