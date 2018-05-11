package com.popo.leancloudtest.moudles.Chat;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by popo on 2018/5/10.
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetectorCompat mGestureDetectorCompat;//手势探测器
    private RecyclerView mRecyclerView;
    private ItemTouchHelper ItemTouchHelper;

    public OnRecyclerItemClickListener(RecyclerView recyclerView,ItemTouchHelper itemTouchHelper) {
        mRecyclerView = recyclerView;
        ItemTouchHelper=itemTouchHelper;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),
                new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{

        //长按屏幕超过一定时长，就会触发，就是长按事件
        @Override
        public void onLongPress(MotionEvent e) {

            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onLongClick(childViewHolder);
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            ItemTouchHelper.attachToRecyclerView(mRecyclerView);
            if (e1.getX() - e2.getX() > 400) {
               /*int postion=mRecyclerView.getAdapter().getItemCount()-1;
                if(postion>0){
                    View childViewUnder = mRecyclerView.findChildViewUnder(e1.getX(), e1.getY());
                    if (childViewUnder != null) {
                        RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                        ItemTouchHelper.startSwipe(childViewHolder);
                    }
                }*/
                Log.i("hhh", "向左滑...");
                return true;
            }
            if (e2.getX() - e1.getX() > 400) {
                Log.i("hhh", "向右滑...");
                /*int postion=mRecyclerView.getAdapter().getItemCount()-1;
                if(postion>0){
                    View childViewUnder = mRecyclerView.findChildViewUnder(e1.getX(), e1.getY());
                    if (childViewUnder != null) {
                        RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                        ItemTouchHelper.startSwipe(childViewHolder);
                    }
                }*/
                return true;
            }
            if (e1.getY() - e2.getY() > 400) {
                Log.i("hhh", "向上滑...");
                return true;
            }
            if (e2.getY() - e1.getY() > 400) {
                Log.i("hhh", "向下滑...");
                return true;
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("hhh","double click");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
            }
            return true;
        }

    }
}
