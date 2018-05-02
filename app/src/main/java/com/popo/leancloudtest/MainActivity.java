package com.popo.leancloudtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.avos.avoscloud.AVOSCloud;

import com.avos.avoscloud.AVUser;
import com.popo.leancloudtest.moudles.Chat.ChatScreen;
import com.popo.leancloudtest.moudles.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AVOSCloud.initialize(this,"DfXQ7A2S5dA4k43bxrrpVsei-gzGzoHsz","93TL7GOibAp6i4jVdw9YEQ9h");
        AVOSCloud.setDebugLogEnabled(true);

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            Intent intent=new Intent(MainActivity.this, ChatScreen.class);
            startActivity(intent);
            this.finish();
        } else {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
