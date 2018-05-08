package com.popo.leancloudtest.moudles.Chat;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.popo.leancloudtest.R;

import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends AppCompatActivity {
    MyGestureLayoutView gestureOverlayView1;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        final AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){

                    final ArrayList<AVIMTextMessage> dataList=new ArrayList<>();

                    /*client.createConversation(Arrays.asList("noterp"), "noterp", null, new AVIMConversationCreatedCallback() {
                        @Override
                        public void done(AVIMConversation avimConversation, AVIMException e) {
                            AVIMTextMessage msg=new AVIMTextMessage();
                            msg.setText("hello");
                            avimConversation.sendMessage(msg, new AVIMConversationCallback() {
                                @Override
                                public void done(AVIMException e) {
                                    if(e==null){
                                        Log.d("hhh","send");
                                    }
                                }
                            });
                        }
                    });*/
                    AVIMConversation conversation=client.getConversation("5aebfb86ee920a0046af6276");
                    int limit=10;
                    conversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
                        @Override
                        public void done(List<AVIMMessage> list, AVIMException e) {
                            if(e==null){
                                Log.d("hhh","get");
                                for(AVIMMessage message :list){
                                    dataList.add((AVIMTextMessage)message);
                                }
                                mRecyclerView.setAdapter(new MessageAdapter(dataList));
                                ItemTouchHelper touchHelper = new ItemTouchHelper(new CardItemTouchHelperCallback(mRecyclerView.getAdapter(),dataList));
                                touchHelper.attachToRecyclerView(mRecyclerView);
                                mRecyclerView.setLayoutManager(new CardLayoutManager(mRecyclerView,touchHelper));
                            }
                        }
                    });
                    mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                    gestureOverlayView1 = (MyGestureLayoutView) findViewById(R.id.gestureOverlayView1);
                    gestureOverlayView1.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            mRecyclerView.dispatchTouchEvent(motionEvent);
                            return false;
                        }
                    });
                    //加载手势库文件
                    final GestureLibrary library = GestureLibraries.fromRawResource(ChatScreen.this,R.raw.gesture);
                    library.load();
                    //手势执行监听器
                    //gestureOverlayView1.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
                    gestureOverlayView1.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
                        @Override
                        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                            //识别手势
                            ArrayList<Prediction> mygesture = library.recognize(gesture);
                            Prediction prediction = mygesture.get(0);
                            //prediction.score为相似度
                            //prediction.name为手势的名字
                            if(prediction.score>=2){
                                Log.d("hhh",prediction.name);
                            }
                        }
                    });
                }
                else {
                    Log.d("error",e.getMessage());
                }
            }
        });
    }
}
