package com.popo.leancloudtest.moudles.Chat;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.ArrayList;

import me.yuqirong.cardswipelayout.CardConfig;

/**
 * Created by popo on 2018/5/2.
 */

public class CardItemTouchHelperCallback extends ItemTouchHelper.Callback{
    RecyclerView.Adapter mAdapter;
    ArrayList<AVIMTextMessage> dataList;
    OnSwipeListener mListener=new OnSwipeListener() {
        @Override
        public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            Log.d("hhh","onSwip");
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, AVIMTextMessage t, int direction) {
            Log.d("hhh","onSwiped");
        }

        @Override
        public void onSwipedClear() {
            Log.d("hhh","onclear");

        }
    };

    public CardItemTouchHelperCallback(RecyclerView.Adapter adapter, ArrayList<AVIMTextMessage> dataList) {
        mAdapter = adapter;
        this.dataList = dataList;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags=0;
       if(dataList.size()>1){
           swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
       }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
       if(direction==ItemTouchHelper.LEFT){
           Log.d("hhh","LEFT");
           viewHolder.itemView.setOnTouchListener(null);
           int layoutPosition = viewHolder.getLayoutPosition();
           AVIMTextMessage remove=dataList.remove(layoutPosition);
           mAdapter.notifyDataSetChanged();
           if(mListener!=null){
               mListener.onSwiped(viewHolder, remove, direction == ItemTouchHelper.LEFT ? CardConfig.SWIPED_LEFT : CardConfig.SWIPED_RIGHT);
           }

       }else if(direction==ItemTouchHelper.RIGHT){
           viewHolder.itemView.setOnTouchListener(null);
           mAdapter.notifyDataSetChanged();
           Log.d("hhh","RIGHT");
       }

    }

}
