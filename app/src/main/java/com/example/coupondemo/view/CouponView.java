package com.example.coupondemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.coupondemo.R;

/**
 * Created by painson on 2016/6/4.
 */

public class CouponView extends LinearLayout {
    private Paint mPaint;
    /**
     * 圆间距
     */
    private float mRCircleGap = 8;
    /**
     * 半径
     */
    private float mRRadius = 10;
    /**
     * 圆数量
     */
    private int mRCircleNum;

    /**
     * 剩余空间
     */
    private float mRRemain;

    private float mLRadius = 80;

    /**
     * 锯齿部分背景颜色
     */
    private int mSawtoothColor;

    /**
     * 整体背景颜色
     */
    private int mBackgroundColor;

    /**
     * 锯齿部分宽度占的比例
     */
    private float mSawtoothWeight;

    private RectF mOval;

    public CouponView(Context context) {
        this(context, null);
    }

    public CouponView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CouponView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {

            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CouponView_radius:
                    mRRadius = a.getDimension(attr, 10);
                    break;
                case R.styleable.CouponView_sawtooth_color:
                    mSawtoothColor = a.getColor(attr, 0);
                    break;
                case R.styleable.CouponView_sawtooth_width_gap:
                    mRCircleGap = a.getDimension(attr, 8);
                    break;
                case R.styleable.CouponView_sawtooth_weight:
                    mSawtoothWeight = a.getFloat(attr, 0.65f);
                    break;
                case R.styleable.CouponView_background_color:
                    mBackgroundColor = a.getColor(attr, 0);
            }
        }
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);

        mOval = new RectF();
        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            //计算剩余空间
            if (mRRemain == 0) {
                mRRemain = (int) (b - t - mRCircleGap - getPaddingTop() - getPaddingBottom()) % (2 * mRRadius + mRCircleGap);
            }
            //计算锯齿半圆个数
            mRCircleNum = (int) ((b - t - mRCircleGap - getPaddingTop() - getPaddingBottom()) / (2 * mRRadius + mRCircleGap));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        mPaint.setColor(mSawtoothColor);
        //画锯齿背景
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + (width * mSawtoothWeight), getPaddingTop() + height, mPaint);

        mPaint.setColor(mBackgroundColor);
        //画锯齿部分
        for (int i = 0; i < mRCircleNum; i++) {
            float y = mRCircleGap + mRRadius + mRRemain / 2 + ((mRCircleGap + mRRadius * 2) * i) + getPaddingTop();
            canvas.drawCircle(getPaddingLeft() + width * mSawtoothWeight, y, mRRadius, mPaint);
        }
        //画其余部分
        canvas.drawRect(getPaddingLeft() + width * mSawtoothWeight, getPaddingTop(), width + getPaddingLeft(), height + getPaddingTop(), mPaint);
//        canvas.drawCircle(getPaddingLeft(), height / 2 + getPaddingTop(), mLRadius, mPaint);
        //画左边的大的半圆
        mOval.set(getPaddingLeft() - mLRadius,
                getPaddingTop() + (height - 2 * mLRadius) / 2,
                getPaddingLeft() + mLRadius,
                getPaddingTop() + (height - 2 * mLRadius) / 2 + 2 * mLRadius);
        canvas.drawArc(mOval, -90, 180, true, mPaint);
    }
}
