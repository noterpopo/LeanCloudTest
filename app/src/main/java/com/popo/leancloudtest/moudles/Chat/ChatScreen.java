package com.popo.leancloudtest.moudles.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.popo.leancloudtest.R;

import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<ArrayList<AVIMTextMessage>> convsList=new ArrayList<>();
    int currentConvesation;


    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message,AVIMConversation conversation,AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Log.d("hhh",((AVIMTextMessage)message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
        final AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){

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
                    AVIMConversationsQuery query = client.getConversationsQuery();
                    query.findInBackground(new AVIMConversationQueryCallback(){
                        @Override
                        public void done(List<AVIMConversation> convs,AVIMException e){
                            if(e==null){
                                int i=0;
                                for(;i<convs.size();++i){
                                    convsList.add(new ArrayList<AVIMTextMessage>());
                                    updateDataList(convs.get(i),i);
                                }
                            }
                        }
                    });
                    mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ChatScreen.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setOnTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
                        @Override
                        public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                            Log.d("hhh","click");
                        }

                        @Override
                        public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                            Log.d("hhh","long click");
                        }

                        @Override
                        public void onUp() {
                            if(currentConvesation>0){
                                currentConvesation--;
                                ((MessageAdapter)(mRecyclerView.getAdapter())).setmMessages(convsList.get(currentConvesation));
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onDowned() {
                            if(currentConvesation<convsList.size()-1){
                                currentConvesation++;
                                ((MessageAdapter)(mRecyclerView.getAdapter())).setmMessages(convsList.get(currentConvesation));
                                mRecyclerView.getAdapter().notifyDataSetChanged();
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
    private void updateDataList(AVIMConversation conversation, final int position){
        int limit=10;
        conversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if(e==null){
                    Log.d("hhh","get");
                    for(AVIMMessage message :list){
                        convsList.get(position).add((AVIMTextMessage)message);
                        convsList.get(position).add((AVIMTextMessage)message);
                    }
                    mRecyclerView.setAdapter(new MessageAdapter(convsList.get(position)));
                    currentConvesation=position;
                }
            }
        });
    }
}
