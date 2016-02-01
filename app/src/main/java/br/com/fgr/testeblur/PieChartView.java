package br.com.fgr.testeblur;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class PieChartView extends View implements Runnable {

    private int angle;
    private int maxAngle;
    private int percent;
    private int points;
    private String color;
    private String alphaColor;
    private int velocity;

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {

        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PieChartView, 0, 0);

        try {

            percent = ta.getInteger(R.styleable.PieChartView_percent, 0);
            points = ta.getInteger(R.styleable.PieChartView_points, 0);
            color = ta.getString(R.styleable.PieChartView_pieChartColor);
            alphaColor = ta.getString(R.styleable.PieChartView_pieChartAlphaColor);
            velocity = ta.getInteger(R.styleable.PieChartView_velocity, 1);

        } finally {
            ta.recycle();
        }

        validateItems();

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

        pSolidoTransp.setColor(Color.TRANSPARENT);
        pSolidoTransp.setStyle(Paint.Style.STROKE);
        pSolidoTransp.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        pSolidoTransp.setStrokeWidth(2);

        pBlur.setColor(Color.parseColor(color));

        canvas.drawArc(rectF2, 270, angle, true, pBlur);

        pBlur.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));

        // Círculo interno
        canvas.drawCircle(500, 500, 200, pSolido);
        // Círculo externo
        canvas.drawCircle(500, 500, 215, pSolidoTransp);

        pBlur.setColor(Color.parseColor(alphaColor));
        canvas.drawArc(rectF1, 270, angle, true, pBlur);

        Paint textPercent = new Paint();
        Paint imagePercent = new Paint();

        textPercent.setColor(Color.parseColor(color));
        imagePercent.setColor(Color.parseColor(color));
        textPercent.setTextSize(Measure.getPixelsFromDP(getContext(), 40));
        imagePercent.setTextSize(Measure.getPixelsFromDP(getContext(), 17));
        textPercent.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));

        canvas.drawText(percent + "", 430, 475, textPercent);
        canvas.drawText("%", 560, 475, imagePercent);

        Paint textPoints = new Paint();

        textPoints.setColor(Color.parseColor(color));
        textPoints.setTextSize(Measure.getPixelsFromDP(getContext(), 19));
        textPoints.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText(pointsFormatted(), 430, 590, textPoints);
        textPoints.setTextSize(Measure.getPixelsFromDP(getContext(), 17));
        canvas.drawText("pontos", 425, 635, textPoints);

        if (angle < maxAngle)
            postDelayed(this, 10);

    }

    @Override
    public void run() {

        angle += velocity;

        if (angle == 360)
            angle = 0;

        invalidate();

    }

    private void validateItems() {

        if (percent < 0 || percent > 100)
            throw new IllegalArgumentException(getContext().getString(R.string.msg_invalid_percent));
        if (velocity <= 0 || velocity > 5)
            throw new IllegalArgumentException(getContext().getString(R.string.msg_invalid_velocity));
        if (color == null)
            color = "#efefef";
        if (alphaColor == null)
            alphaColor = "#77efefef";

        angle = 0;
        maxAngle = 360 * percent / 100;

    }

    private String pointsFormatted() {

        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(new Locale("pt", "BR"));
        DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);

        return df.format(points);

    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
        invalidate();
    }

    public void setPoints(int points) {
        this.points = points;
        invalidate();
    }

    public void setPercent(int percent) {
        this.percent = percent;
        invalidate();
    }

    public void setColors(@NonNull String color, @NonNull String alphaColor) {

        this.color = color;
        this.alphaColor = alphaColor;

        invalidate();

    }

}