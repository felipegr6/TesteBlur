package br.com.fgr.testeblur;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View implements Runnable {

    private int angle;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        angle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint pBlur = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint pSolido = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint pSolidoTransp = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF rectF1 = new RectF();
        RectF rectF2 = new RectF();

        // Arcos
        rectF1.set(300, 300, 700, 700);
        rectF2.set(285, 285, 715, 715);

        pSolido.setColor(Color.WHITE);

        pBlur.setColor(Color.BLUE);

        pSolidoTransp.setColor(Color.TRANSPARENT);
        pSolidoTransp.setStyle(Paint.Style.STROKE);
        pSolidoTransp.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        pSolidoTransp.setStrokeWidth(2);

        canvas.drawArc(rectF2, 270, angle, true, pBlur);

        pBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));

        // Círculo interno
        canvas.drawCircle(500, 500, 200, pSolido);
        // Círculo externo
        canvas.drawCircle(500, 500, 215, pSolidoTransp);

        canvas.drawArc(rectF1, 270, angle, true, pBlur);

        postDelayed(this, 10);

    }

    @Override
    public void run() {

        angle += 1;

        if (angle == 360)
            angle = 0;

        invalidate();

    }

}