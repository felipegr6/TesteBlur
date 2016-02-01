package br.com.fgr.testeblur;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

        int outerRadius = 215;
        int innerRadius = 200;

        int arcSweep = 359;
        int arcOffset = -90;

        RectF outerRect = new RectF(500 - outerRadius, 500 - outerRadius, 500 + outerRadius, 500 + outerRadius);
        RectF innerRect = new RectF(500 - innerRadius, 500 - innerRadius, 500 + innerRadius, 500 + innerRadius);

        Path path = new Path();
        path.arcTo(outerRect, arcOffset + angle, arcSweep);
        path.arcTo(innerRect, arcOffset + arcSweep + angle, -arcSweep);
        path.close();

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.parseColor(ColorsEnum.LIGHT_GRAY.getColors()));
        canvas.drawPath(path, fill);

        Paint border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(2);
        canvas.drawPath(path, border);

        Paint pBlur = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint pSolido = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint pSolidoTransp = new Paint(Paint.ANTI_ALIAS_FLAG);

        RectF rectF1 = new RectF();
        RectF rectF2 = new RectF();

        // Arcos
        rectF1.set(300, 300, 700, 700);
        rectF2.set(285, 285, 715, 715);

        pSolido.setColor(Color.WHITE);

        pBlur.setColor(Color.parseColor(ColorsEnum.DIAMOND.getColors()));

        pSolidoTransp.setColor(Color.TRANSPARENT);
        pSolidoTransp.setStyle(Paint.Style.STROKE);
        pSolidoTransp.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        pSolidoTransp.setStrokeWidth(2);

        pBlur.setColor(Color.parseColor(ColorsEnum.DIAMOND.getColors()));
        canvas.drawArc(rectF2, 270, angle, true, pBlur);

        pBlur.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));

        // Círculo interno
        canvas.drawCircle(500, 500, 200, pSolido);
        // Círculo externo
        canvas.drawCircle(500, 500, 215, pSolidoTransp);

        pBlur.setColor(Color.parseColor(ColorsEnum.ALPHA_DIAMOND.getColors()));
        canvas.drawArc(rectF1, 270, angle, true, pBlur);

        if (angle < 10)
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