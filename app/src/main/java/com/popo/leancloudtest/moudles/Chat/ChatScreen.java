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

import com.popo.leancloudtest.R;

import java.util.ArrayList;

public class ChatScreen extends AppCompatActivity {
    GestureOverlayView gestureOverlayView1;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        //AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        /*client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){

                }
                else {
                    Log.d("error",e.getMessage());
                }
            }
        });*/
        ArrayList<Message> dataList=new ArrayList<>();
        dataList.add(new Message("popo","hh"));
        dataList.add(new Message("sds","sds"));
        dataList.add(new Message("sds","jkuk"));
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new MessageAdapter(dataList));
        ItemTouchHelper touchHelper = new ItemTouchHelper(new CardItemTouchHelperCallback((MessageAdapter)mRecyclerView.getAdapter(),dataList));
        mRecyclerView.setLayoutManager(new CardLayoutManager(mRecyclerView,touchHelper));
        gestureOverlayView1 = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
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
                    Log.d("data",prediction.name);
                }
            }
        });
    }
}
