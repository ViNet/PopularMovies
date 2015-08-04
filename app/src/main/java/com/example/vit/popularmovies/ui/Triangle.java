package com.example.vit.popularmovies.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * view with triangle inside
 * 1,2,3 - vertices
     1
     *   *
     *       3
     *   *
     2
 */
public class Triangle extends View {
    Paint paint;
    Path path;
    final int color = Color.WHITE;

    public Triangle(Context context) {
        super(context);
        init();
    }

    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Triangle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //calculate vertex coordinates
        int x1,y1,x2,y2,x3,y3;
        x1 = getPaddingLeft();
        y1 = getPaddingTop();
        x2 = getPaddingLeft();
        y2 = canvas.getHeight() - getPaddingBottom();
        x3 = canvas.getWidth()  - getPaddingRight();
        y3 = (canvas.getHeight()/2) + getPaddingTop() - getPaddingBottom();

        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.lineTo(x1, y1);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
    }
}
