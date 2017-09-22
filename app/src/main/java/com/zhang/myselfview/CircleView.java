package com.zhang.myselfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhang on 2017/9/20.
 */

public class CircleView extends View{
    private Paint mPaint_1,mPaint_2;
    private int mColor;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);//这个构造必须重写 不然用户没有传入第三个参数的时候走这个
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            // 加载自定义属性集合CircleView
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleView);

            // 解析集合中的属性circle_color属性
            // 该属性的id为:R.styleable.CircleView_circle_color
            // 将解析的属性传入到画圆的画笔颜色变量当中（本质上是自定义画圆画笔的颜色）
            // 第二个参数是默认设置颜色（即无指定circle_color情况下使用）
            mColor = a.getColor(R.styleable.CircleView_circle_color, Color.GREEN);

            // 解析后释放资源
            a.recycle();

            init();
    }

    private void init() {
        mPaint_1=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_1.setStrokeWidth(5f);
        mPaint_1.setColor(mColor);
        mPaint_1.setStyle(Paint.Style.FILL);//设置画笔为填充的方式

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //手动支持padding属性
        int paddingLeft= getPaddingLeft();
        int paddingRight=getPaddingRight();
        int paddingTop=getPaddingTop();
        int paddingBottom=getPaddingBottom();

        //宽高要考虑 padding属性
        int width=getWidth()-paddingLeft-paddingRight;
        int height=getHeight()-paddingBottom-paddingTop;
        int r=Math.min(width,height)/2;

        // 圆心 = 控件的中央,半径 = 宽,高最小值的2分之1
        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,r,mPaint_1);
    }
}
