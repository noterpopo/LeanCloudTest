package com.popo.leancloudtest.moudles.Chat;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;

import me.yuqirong.cardswipelayout.CardConfig;

/**
 * Created by popo on 2018/5/2.
 */

public class CardItemTouchHelperCallback extends ItemTouchHelper.Callback{
    MessageAdapter mAdapter;
    ArrayList<Message> dataList;
    OnSwipeListener mListener=new OnSwipeListener() {
        @Override
        public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            Log.d("message","onSwip");
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, Message t, int direction) {
            Log.d("message","onSwiped");
        }

        @Override
        public void onSwipedClear() {
            Log.d("message","onclear");

        }
    };

    public CardItemTouchHelperCallback(MessageAdapter adapter, ArrayList<Message> dataList) {
        mAdapter = adapter;
        this.dataList = dataList;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.itemView.setOnTouchListener(null);
        int layoutPosition = viewHolder.getLayoutPosition();
        Message remove=dataList.remove(layoutPosition);
        mAdapter.notifyDataSetChanged();
        if(mListener!=null){
            mListener.onSwiped(viewHolder, remove, direction == ItemTouchHelper.LEFT ? CardConfig.SWIPED_LEFT : CardConfig.SWIPED_RIGHT);
        }
        if (mAdapter.getItemCount() == 0) {
            if (mListener != null) {
                mListener.onSwipedClear();
            }
        }

    }
}
