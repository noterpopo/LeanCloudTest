package com.popo.leancloudtest.moudles.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.popo.leancloudtest.R;

import java.util.ArrayList;

/**
 * Created by popo on 2018/5/2.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private ArrayList<AVIMTextMessage> mMessages;

    public void setmMessages(ArrayList<AVIMTextMessage> mMessages) {
        this.mMessages = mMessages;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        MessageViewHolder(View itemView){
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.contentTextView);
        }
    }

    public MessageAdapter(ArrayList<AVIMTextMessage> messages) {
        mMessages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.mTextView.setText(mMessages.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public void remove(int postion){
        mMessages.remove(postion);
        notifyDataSetChanged();

    }
}
