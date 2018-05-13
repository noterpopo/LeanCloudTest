package com.popo.leancloudtest.moudles.Chat;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class OnRecyclerItemClickListener implements View.OnTouchListener{
    private GestureDetectorCompat mGestureDetectorCompat;//手势探测器
    private RecyclerView mRecyclerView;
    private int currentPosition;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        currentPosition=mRecyclerView.getChildCount()-1;

        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),
                new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mGestureDetectorCompat.onTouchEvent(motionEvent);
        return true;
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onUp();
    public abstract void onDowned();

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
            if (e1.getX() - e2.getX() > 400&&currentPosition < mRecyclerView.getAdapter().getItemCount()) {
                currentPosition++;
                mRecyclerView.smoothScrollToPosition(currentPosition);

                Log.d("hhh", "向左滑...");
            }
            if (e2.getX() - e1.getX() > 400&&currentPosition >0) {
                Log.d("hhh", "向右滑...");
                currentPosition--;
                mRecyclerView.smoothScrollToPosition(currentPosition);

            }
            if (e1.getY() - e2.getY() > 400) {
                Log.i("hhh", "向上滑...");
                onUp();
            }
            if (e2.getY() - e1.getY() > 400) {
                Log.i("hhh", "向下滑...");
                onDowned();
            }
            return true;
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