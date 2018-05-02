package com.popo.leancloudtest.moudles.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popo.leancloudtest.R;

import java.util.ArrayList;

/**
 * Created by popo on 2018/5/2.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private ArrayList<Message> mMessages;

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        MessageViewHolder(View itemView){
            super(itemView);
            mImageView=(ImageView)itemView.findViewById(R.id.iv_avatar);
        }
    }

    public MessageAdapter(ArrayList<Message> messages) {
        mMessages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
