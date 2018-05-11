package com.popo.leancloudtest.moudles.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

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
                    mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                    mRecyclerView.setLayoutManager(new CardLayoutManager(mRecyclerView));
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
                                ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new CardItemTouchHelperCallback(mRecyclerView.getAdapter(),dataList));
                                mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView,itemTouchHelper) {
                                    @Override
                                    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                                        Log.d("hhh","Click");
                                    }

                                    @Override
                                    public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                                        Log.d("hhh","Long Click");
                                    }
                                });
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
