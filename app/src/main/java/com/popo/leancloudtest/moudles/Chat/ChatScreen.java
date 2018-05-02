package com.popo.leancloudtest.moudles.Chat;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.popo.leancloudtest.R;

import java.util.ArrayList;

public class ChatScreen extends AppCompatActivity {
    GestureOverlayView gestureOverlayView1;
    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){
                    gestureOverlayView1 = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
                    editText1 = (EditText) findViewById(R.id.editText1);
                    //加载手势库文件
                    final GestureLibrary library = GestureLibraries.fromRawResource(ChatScreen.this,R.raw.gesture);
                    library.load();
                    //手势执行监听器
                    gestureOverlayView1.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
                    gestureOverlayView1.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
                        @Override
                        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                            //识别手势
                            ArrayList<Prediction> mygesture = library.recognize(gesture);
                            Prediction prediction = mygesture.get(0);
                            //prediction.score为相似度
                            //prediction.name为手势的名字
                            if(prediction.score>=1.0){
                                editText1.setText(editText1.getText()+prediction.name);
                            }
                        }
                    });
                }
            }
        });
    }
}
