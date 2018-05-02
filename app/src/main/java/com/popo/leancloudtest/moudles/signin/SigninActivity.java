package com.popo.leancloudtest.moudles.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.popo.leancloudtest.R;

public class SigninActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences=null;
    private EditText username=null;
    private EditText password=null;
    private Button signin=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username=(EditText)findViewById(R.id.SuserName);
        password=(EditText)findViewById(R.id.Spassword);
        signin=(Button)findViewById(R.id.Ssignin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String un=username.getText().toString();
                final String psw=password.getText().toString();
                AVUser user = new AVUser();// 新建 AVUser 对象实例
                user.setUsername(un);// 设置用户名
                user.setPassword(psw);// 设置密码
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Intent intent=new Intent();
                            intent.putExtra("username",un);
                            intent.putExtra("psaaword",psw);
                            SigninActivity.this.setResult(0,intent);
                            SigninActivity.this.finish();
                        } else {
                            Log.d("error",e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
