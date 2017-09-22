package com.zhang.myselfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by zhang on 2017/9/19.
 */

public class MyListView extends ListView implements View.OnTouchListener,
        GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    private OnDeleteListener listener;

    private View deleteButton;

    private ViewGroup itemLayout;

    private int selectedItem;

    private boolean isDeleteShown;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
    }

    public void setOnDeleteListener(OnDeleteListener l) {
        listener = l;
    }
    /*
         * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，将捕捉到的MotionEvent交给GestureDetector
         * 来分析是否有合适的callback函数来处理用户的手势
         */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDeleteShown) {
            itemLayout.removeView(deleteButton);
            deleteButton = null;
            isDeleteShown = false;
            //如果删除按钮已经显示 那么当touch的时候就去掉删除按钮
            return false;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!isDeleteShown) {
            selectedItem = pointToPosition((int) e.getX(), (int) e.getY());//返回的是按下的那个点所在的listview的item
        }
        return false;
    }
    //final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;

    // 触发条件 ：
    // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
    // 参数解释：
    // e1：第1个ACTION_DOWN MotionEvent
    // e2：最后一个ACTION_MOVE MotionEvent
    // velocityX：X轴上的移动速度，像素/秒
    // velocityY：Y轴上的移动速度，像素/秒
    //    if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
    //                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
    //        // Fling left
    //        Log.i("MyGesture", "Fling left");
    //        Toast.makeText(MainActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
    //    } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
    //                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
    //        // Fling right
    //        Log.i("MyGesture", "Fling right");
    //        Toast.makeText(MainActivity.this, "Fling Right", Toast.LENGTH_SHORT).show();
    //    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)&&e2.getX()-e1. getX()<0) {
            deleteButton = LayoutInflater.from(getContext()).inflate(
                    R.layout.delete_button, null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(deleteButton);
                    deleteButton = null;
                    isDeleteShown = false;
                    listener.onDelete(selectedItem);
                }
            });
            itemLayout = (ViewGroup) getChildAt(selectedItem
                    - getFirstVisiblePosition());//这里确定itemLayout是等于父ListView中的那个子项  然后在这个子项上添加一个button
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton, params);
            isDeleteShown = true;
        }
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    public interface OnDeleteListener {

        void onDelete(int index);

    }

}